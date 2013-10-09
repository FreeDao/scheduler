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
public class ContextListener implements ItemReadListener<Map<String, Object>>, ItemWriteListener<Map<String, Object>>,
		ChunkListener, ItemProcessListener<Map<String, Object>, Map<String, Object>> {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	public void beforeProcess(Map<String, Object> item) {
		System.out.println("DataListener-beforeProcess>>" + item);
		log.info("DataListener-beforeProcess>>" + item);
	}

	public void afterProcess(Map<String, Object> item, Map<String, Object> result) {
		System.out.println("DataListener-afterProcess>>" + item + ", result>>" + result);
		log.info("DataListener-afterProcess>>" + item + ", result>>" + result);
	}

	public void onProcessError(Map<String, Object> item, Exception e) {
		System.out.println("DataListener-onProcessError>>" + item);
		e.printStackTrace();
		log.info("DataListener-onProcessError>>" + item, e);
	}

	public void beforeChunk(ChunkContext context) {
		System.out.println("DataListener-beforeChunk>>" + context);
		log.info("DataListener-beforeChunk>>" + context);
	}

	public void afterChunk(ChunkContext context) {
		System.out.println("DataListener-afterChunk>>" + context);
		log.info("DataListener-afterChunk>>" + context);
	}

	public void afterChunkError(ChunkContext context) {
		System.out.println("DataListener-afterChunkError>>" + context);
		log.info("DataListener-afterChunkError>>" + context);
	}

	public void beforeWrite(List<? extends Map<String, Object>> items) {
		log.info("DataListener-beforeWrite>>" + items);
	}

	public void afterWrite(List<? extends Map<String, Object>> items) {
		System.out.println("DataListener-afterWrite>>" + items);
		log.info("DataListener-afterWrite>>" + items);
	}

	public void onWriteError(Exception exception, List<? extends Map<String, Object>> items) {
		System.out.println("DataListener-onWriteError>>" + items);
		exception.printStackTrace();
		log.info("DataListener-onWriteError>>" + items, exception);
	}

	public void beforeRead() {
		System.out.println("DataListener-beforeRead>>");
		log.info("DataListener-beforeRead>>");
	}

	public void afterRead(Map<String, Object> item) {
		System.out.println("DataListener-afterRead>>" + item);
		log.info("DataListener-afterRead>>" + item);
	}

	public void onReadError(Exception ex) {
		System.out.println("DataListener-onReadError>>");
		ex.printStackTrace();
		log.info("DataListener-onReadError>>", ex);
	}
}