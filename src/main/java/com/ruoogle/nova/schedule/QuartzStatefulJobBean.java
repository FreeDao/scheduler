package com.ruoogle.nova.schedule;

import java.util.HashMap;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;

/**
 * 任务Job.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年9月28日 下午4:58:13
 */
public abstract class QuartzStatefulJobBean implements StatefulJob {

	private HashMap<String, String> enviroment;

	private final static String ENV_KEY = "env";

	private final static String PRODUCT_ENV_VALUE = "prd";

	protected static final Logger log = LoggerFactory.getLogger("Scheduler");
	
	protected JobLauncher jobLauncher;
	
	public static class A {
		private int id;

		public A(int id) {
			super();
			this.id = id;
		}
	}

	public static void main(String[] args) {
		A a = new A(3);
//		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(a);
		
		ConfigurablePropertyAccessor accessor = PropertyAccessorFactory.forDirectFieldAccess(a);
		
		System.out.println(accessor.getPropertyValue("id"));
		accessor.setPropertyValue("id", 15);
		System.out.println(accessor.getPropertyValue("id"));
	}
	
	/**
	 * This implementation applies the passed-in job data map as bean property
	 * values, and delegates to <code>executeInternal</code> afterwards.
	 * 
	 * @see #executeInternal
	 */
	public final void execute(JobExecutionContext context)
			throws JobExecutionException {

		try {
			BeanWrapper bw = PropertyAccessorFactory
					.forBeanPropertyAccess(this);
			MutablePropertyValues pvs = new MutablePropertyValues();
			pvs.addPropertyValues(context.getScheduler().getContext());
			pvs.addPropertyValues(context.getMergedJobDataMap());
			bw.setPropertyValues(pvs, true);
		} catch (SchedulerException ex) {
			throw new JobExecutionException(ex);
		}

		if (enviroment != null && enviroment.get(ENV_KEY) != null) {
			if (!PRODUCT_ENV_VALUE.equals(enviroment.get(ENV_KEY))) {
				log.error("not prd env return!!" + getClass().getName());
				return;
			}
		}
		
		log.error("job start:" + getClass().getName());
		try {
			executeInternal(context);
		} catch (Exception e) {
			log.error("job run error.:" + getClass().getName(), e);
		}
		log.error("job end:" + getClass().getName());
	}

	/**
	 * Execute the actual job. The job data map will already have been applied
	 * as bean property values by execute. The contract is exactly the same as
	 * for the standard Quartz execute method.
	 * 
	 * @see #execute
	 */
	protected abstract void executeInternal(JobExecutionContext context)
			throws JobExecutionException;

	/**
	 * @param enviroment the enviroment to set
	 */
	public void setEnviroment(HashMap<String, String> enviroment) {
		this.enviroment = enviroment;
	}
	
	/**
	 * @param jobLauncher the jobLauncher to set
	 */
	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}
}