package com.ruoogle.nova;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年9月28日 下午5:33:45
 */
public class ITester {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("/schedule/job/*.xml");
		JobLauncher jobLauncher = context.getBean("jobLauncher", JobLauncher.class);
		Job job = context.getBean("marketLevelJob", Job.class);
		System.out.println(job);
		try {
			JobExecution result = jobLauncher.run(job, 
					new JobParametersBuilder()
					.addParameter("time", new JobParameter(new Date()))
					.addParameter("id", new JobParameter(5L))
					.toJobParameters());
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
