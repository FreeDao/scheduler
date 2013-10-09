/**
 * 
 */
package com.ruoogle.nova.common;

import java.beans.Introspector;

import javax.servlet.ServletContextEvent;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.web.util.Log4jWebConfigurer;
import org.springframework.web.util.WebUtils;

/**
 * @author 刘飞
 * E-mail:liufei_it@126.com
 * 9:22:51 AM Sep 30, 2013
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		log.error("ContextLoaderListener--contextInitialized...");
		WebUtils.setWebAppRootSystemProperty(event.getServletContext());
		log.error("ContextLoaderListener--WebUtils.setWebAppRootSystemProperty...");
		Log4jWebConfigurer.initLogging(event.getServletContext());
		log.error("ContextLoaderListener--Log4jWebConfigurer.initLogging...");
		CachedIntrospectionResults.acceptClassLoader(Thread.currentThread().getContextClassLoader());
		log.error("ContextLoaderListener--CachedIntrospectionResults.acceptClassLoader...");
		Scheduler scheduler = getCurrentWebApplicationContext().getBean("novaScheduler", Scheduler.class);
		try {
			if(scheduler.isStarted()) {
				log.error(String.format("novaScheduler named[%s] init and run success.", scheduler.getSchedulerName()));
			} else {
				scheduler.start();
				log.error(String.format("novaScheduler named[%s] init and run success.", scheduler.getSchedulerName()));
			}
			String[] triggerGroupNames = scheduler.getTriggerGroupNames();
			if(triggerGroupNames != null && triggerGroupNames.length > 0) {
				for (String group : triggerGroupNames) {
					String[] triggerNames = scheduler.getTriggerNames(group);
					if(triggerNames != null && triggerNames.length > 0) {
						for (String triggerName : triggerNames) {
							log.error(String.format("trigger named %s in group %s success.", triggerName, group));
						}
					}
				}
			}
			String[] jobGroupNames = scheduler.getJobGroupNames();
			if(jobGroupNames != null && jobGroupNames.length > 0) {
				for (String jobGroupName : jobGroupNames) {
					String[] jobNames = scheduler.getJobNames(jobGroupName);
					if(jobNames != null && jobNames.length > 0) {
						for (String jobName : jobNames) {
							log.error(String.format("job named %s in group %s success.", jobName, jobGroupName));
						}
					}
				}
			}
		} catch (SchedulerException e) {
			log.error("novaScheduler init and run error.", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		log.error("ContextLoaderListener--contextDestroyed...");
		WebUtils.removeWebAppRootSystemProperty(event.getServletContext());
		log.error("ContextLoaderListener--WebUtils.removeWebAppRootSystemProperty...");
		Log4jWebConfigurer.shutdownLogging(event.getServletContext());
		log.error("ContextLoaderListener--Log4jWebConfigurer.shutdownLogging...");
		CachedIntrospectionResults.clearClassLoader(Thread.currentThread().getContextClassLoader());
		log.error("ContextLoaderListener--CachedIntrospectionResults.clearClassLoader...");
		Introspector.flushCaches();
		log.error("ContextLoaderListener--Introspector.flushCaches...");
	}
}