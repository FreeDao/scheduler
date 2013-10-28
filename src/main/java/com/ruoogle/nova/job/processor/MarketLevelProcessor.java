/**
 * 
 */
package com.ruoogle.nova.job.processor;

import java.util.Map;

/**
 * 运营人员试用期到正式的转换.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com 11:56:34 AM Sep 29, 2013
 */
public class MarketLevelProcessor extends MapDataItemProcessor {

	@Override
	protected Map<String, Object> processInternal(Map<String, Object> item)
			throws Exception {
		log.error("MarketLevelProcessor>>" + item);
		return item;
	}

	
}