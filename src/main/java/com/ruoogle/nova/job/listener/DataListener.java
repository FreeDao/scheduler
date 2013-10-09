package com.ruoogle.nova.job.listener;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * 数据监听。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年9月28日 下午4:33:25
 */
public class DataListener implements ItemReadListener<Map<String, Object>>, ItemWriteListener<Map<String, Object>>,
		ChunkListener, ItemProcessListener<Map<String, Object>, Map<String, Object>> {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	public void beforeProcess(Map<String, Object> item) {
		log.debug("DataListener-beforeProcess>>" + item);
	}

	public void afterProcess(Map<String, Object> item, Map<String, Object> result) {
		log.debug("DataListener-afterProcess>>" + item + ", result>>" + result);
	}

	public void onProcessError(Map<String, Object> item, Exception e) {
		log.debug("DataListener-onProcessError>>" + item, e);
	}

	public void beforeChunk(ChunkContext context) {
		log.debug("DataListener-beforeChunk>>" + context);
	}

	public void afterChunk(ChunkContext context) {
		log.debug("DataListener-afterChunk>>" + context);
	}

	public void afterChunkError(ChunkContext context) {
		log.debug("DataListener-afterChunkError>>" + context);
	}

	public void beforeWrite(List<? extends Map<String, Object>> items) {
		log.debug("DataListener-beforeWrite>>" + items);
	}

	public void afterWrite(List<? extends Map<String, Object>> items) {
		log.debug("DataListener-afterWrite>>" + items);
	}

	public void onWriteError(Exception exception, List<? extends Map<String, Object>> items) {
		log.debug("DataListener-onWriteError>>" + items, exception);
	}

	public void beforeRead() {
		log.debug("DataListener-beforeRead>>");
	}

	public void afterRead(Map<String, Object> item) {
		log.debug("DataListener-afterRead>>" + item);
	}

	public void onReadError(Exception ex) {
		log.debug("DataListener-onReadError>>", ex);
	}
}