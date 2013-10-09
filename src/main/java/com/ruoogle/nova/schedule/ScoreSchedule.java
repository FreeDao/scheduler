/**
 * 
 */
package com.ruoogle.nova.schedule;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;

/**
 * 连续2周分数没有过4000的取消运营资格任务.
 * 
 * @author 刘飞
 * E-mail:liufei_it@126.com
 * 12:13:27 PM Sep 29, 2013
 */
@SuppressWarnings("deprecation")
public class ScoreSchedule extends QuartzStatefulJobBean {
	
	protected NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log.error("ScoreSchedule>> start...");
		List<Integer> userIdList = getAllMarketUsrId();
		if(CollectionUtils.isEmpty(userIdList)) {
			return;
		}
		for (Integer userid : userIdList) {
			//统计14天的积分数.
			int times = 0;//连续几周的积分低于4000
			Date now = new Date();
			int index = 1;
			int weeks = 2;//2周
			try {
				long scoreCount = 0L;//统计7天的积分数目
				int dates = weeks * 7;//一共统计4周,14天
				do{
					int day = getDay(now, -index);
					
					long score = getScoreByDay(day, userid);
					
					if(-100L == score) {
						break;
					}
					
					if(index % 7 == 0) {
						if(scoreCount < 4000) {
							times++;
						}
						scoreCount = 0L;
					}
					scoreCount += score;
					index++;
				} while(index <= dates);
				
			} catch (Exception e) {
				log.error("ScoreSchedule execute error.", e);
			}
			if(times >= weeks) {
				updateMarketLevel(userid);
			}
		}
		log.error("ScoreSchedule>> end...");
	}
	
	private int getDay(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, amount);
//		return DateUtil.formatDate("yyyyMMdd", calendar.getTime());
		return calDate(calendar.getTime());
	}
	
	private int calDate(Date date) {
		return (date.getYear() +1900)* 10000 + (date.getMonth() + 1) * 100 + date.getDate();
	}
	
	private void updateMarketLevel(int userid) {
		String sql = "update nova_extend_marketing set level = 0 where id = :id";
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", userid);
			namedJdbcTemplate.update(sql, paramMap);
		} catch(Exception e) {
			log.error("updateMarketLevel error.", e);
			log.error("SQL:" + "update nova_extend_marketing set level = 0 where id = " + userid);
		}
	}
	
	private long getScoreByDay(int day, int userid) {
		try{
			String sql = "SELECT count FROM nova_extend_marketing_statistics WHERE marketId = :marketId and day = :day and type = 12";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("day", day);
			paramMap.put("marketId", userid);
			
			String countSQL = "SELECT count(*) FROM nova_extend_marketing_statistics WHERE marketId = :marketId and day = :day and type = 12";
			
			Integer count = namedJdbcTemplate.queryForObject(countSQL, paramMap, Integer.class);
			if(count == null || count <= 0) {
				return -100L;
			}
			
			return namedJdbcTemplate.queryForObject(sql, paramMap, Long.class);
		} catch(Exception e) {
			if(e instanceof EmptyResultDataAccessException) {
				log.error("EmptyResultDataAccessException ignore...");
			} else {
				log.error("getScoreByDay error.", e);
				log.error("SQL:" + "SELECT count FROM nova_extend_marketing_statistics WHERE marketId = " + userid + " and day = " + day + " and type = 12");
			}
		}
		return 0L;
	}
	
	private List<Integer> getAllMarketUsrId() {
		try {
			return namedJdbcTemplate.queryForList("select distinct id from nova_extend_marketing where userFrom = 3", new HashMap<String, Object>(), Integer.class);
		} catch (Exception e) {
			log.error("getAllMarketUsrId error.", e);
		}
		return Collections.emptyList();
	}
	
	/**
	 * @param namedJdbcTemplate the namedJdbcTemplate to set
	 */
	public void setNamedJdbcTemplate(
			NamedParameterJdbcTemplate namedJdbcTemplate) {
		this.namedJdbcTemplate = namedJdbcTemplate;
	}
}
