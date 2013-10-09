/**
 * 
 */
package com.ruoogle.nova.job.processor;

import java.util.Date;
import java.util.Map;

import com.ruoogle.nova.common.DateUtil;

/**
 * 运营人员试用期到正式的转换.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com 11:56:34 AM Sep 29, 2013
 */
public class MarketLevelProcessor extends MapDataItemProcessor {

	@Override
	protected Map<String, Object> processInternal(Map<String, Object> item)
			throws Exception {
		Date gmtCreate = (Date) item.get("createdAt");
		log.error(String.format("userSign:%s, createAt:%s will be set level = 2",
				item.get("userSign"), DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", gmtCreate)));
		log.error("MarketLevelProcessor>>" + item);
		return item;
	}

	
}