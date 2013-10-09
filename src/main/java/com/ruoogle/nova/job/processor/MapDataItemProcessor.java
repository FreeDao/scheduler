package com.ruoogle.nova.job.processor;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * 将数据以Map的形式传递、接收。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年9月28日 下午4:14:08
 */
public class MapDataItemProcessor implements ItemProcessor<Map<String, Object>, Map<String, Object>> {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Process the provided item, returning a potentially modified or new item for continued
	 * processing.  If the returned result is null, it is assumed that processing of the item
	 * should not continue.
	 * 
	 * @param item to be processed
	 * @return potentially modified or new item for continued processing, null if processing of the 
	 *  provided item should not continue.
	 * @throws Exception
	 */
	public final Map<String, Object> process(Map<String, Object> item) throws Exception {
		try {
			return processInternal(item);
		} catch(Exception e) {
			log.error("MapDataItemProcessor#process error.", e);
		}
		return item;
	}
	
	protected Map<String, Object> processInternal(Map<String, Object> item) throws Exception {
		return item;
	}
}