/**
 * 
 */
package com.ruoogle.nova.schedule;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 运营人员试用期到正式的转换任务.
 * 
 * @author 刘飞
 * E-mail:liufei_it@126.com
 * 12:13:27 PM Sep 29, 2013
 */
public class MarketLevelSchedule extends QuartzStatefulJobBean {
	
	protected Job marketLevelJob;
	protected NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log.error("MarketLevelSchedule>> start...");
		try {
			JobExecution result = jobLauncher.run(marketLevelJob, 
					new JobParametersBuilder()
					.addParameter("time", new JobParameter(new Date()))
					.addParameter("dates", new JobParameter(14L))
					.addParameter("old_level", new JobParameter(1L))
					.addParameter("new_level", new JobParameter(2L))
					.toJobParameters());
			log.error("MarketLevelSchedule >> level 1 >> 2" + result);
		} catch (Exception e) {
			log.error("MarketLevelSchedule >> level 1 >> 2 execute error.", e);
		}
		try {
			JobExecution result = jobLauncher.run(marketLevelJob, 
					new JobParametersBuilder()
					.addParameter("time", new JobParameter(new Date()))
					.addParameter("dates", new JobParameter(7L))
					.addParameter("old_level", new JobParameter(2L))
					.addParameter("new_level", new JobParameter(3L))
					.toJobParameters());
			log.error("MarketLevelSchedule >> level 2 >> 3" + result);
		} catch (Exception e) {
			log.error("MarketLevelSchedule >> level 2 >> 3 execute error.", e);
		}
		log.error("MarketLevelSchedule>> end...");
		
		/*String sql = "select * from nova_extend_marketing where datediff(now(), createdAt) >= :dates and level = 1";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dates", 14);
		List<Map<String, Object>> list = namedJdbcTemplate.queryForList(sql, paramMap);
		
		if(CollectionUtils.isEmpty(list)) {
			log.error("search result is Empty return!!!");
			return ;
		}
		
		for (Map<String, Object> map : list) {
			String sql0 = "update nova_extend_marketing set level = 2 where id = :id";
			try{
				Map<String, Object> paramMap0 = new HashMap<String, Object>();
				paramMap.put("id", map.get("id"));
				namedJdbcTemplate.update(sql0, paramMap0);
			} catch(Exception e) {
				log.error("updateMarketLevel error.", e);
				log.error("SQL:" + "update nova_extend_marketing set level = 2 where id = " + map.get("id"));
			}
		}*/
	}
	
	/**
	 * @param namedJdbcTemplate the namedJdbcTemplate to set
	 */
	public void setNamedJdbcTemplate(NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}
	
	/**
	 * @param marketLevelJob the marketLevelJob to set
	 */
	public void setMarketLevelJob(Job marketLevelJob) {
		this.marketLevelJob = marketLevelJob;
	}
}
