package com.ruoogle.nova;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年9月28日 下午5:33:45
 */
public class ITester {
	
	public static void main(String[] args) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://172.16.10.79:3306/ruooglenova?useUnicode=true&characterEncoding=utf-8");
		dataSource.setUsername("nova");
		dataSource.setPassword("123456");
		
		
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource("/rank.sql"));
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
		
	}

	public static void main0(String[] args) throws Exception {
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
