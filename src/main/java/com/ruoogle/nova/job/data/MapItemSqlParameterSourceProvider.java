package com.ruoogle.nova.job.data;

import java.util.Map;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * 通过Map来传递中间数据。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年9月28日 下午4:26:23
 */
public class MapItemSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<Map<String, Object>> {

	/**
	 * Provide parameter values in an {@link SqlParameterSource} based on values from  
	 * the provided item.
	 * @param item the item to use for parameter values
	 */
	public SqlParameterSource createSqlParameterSource(Map<String, Object> item) {
		return new MapSqlParameterSource(item);
	}

}