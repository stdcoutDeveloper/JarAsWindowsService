package com.dev;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class LogDaemon {
	private static final Logger LOGGER = LogManager.getLogger(LogDaemon.class);
	private static ScheduledFuture<?> result;
	
	static {
		DOMConfigurator.configure("config/log4j.xml");
	}
	
	public static void start(String[] args) {
		// log
		LOGGER.info("Log service is started.");
		
		// execute task
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		result = exec.scheduleAtFixedRate(new LogWriter() , 0, 3, TimeUnit.SECONDS);
	}
	
	public static void stop(String[] args) {
		// stop executing task
		boolean rs = result.cancel(true);
		LOGGER.debug("Attempt to cancel task: " + rs);
		
		// log
		LOGGER.info("Log service is shut down.");
		
		// terminate
		System.exit(0);
	}
}

class LogWriter implements Runnable {
	
	private static final Logger LOGGER = LogManager.getLogger(LogWriter.class);
	
	public LogWriter() {
		DOMConfigurator.configure("config/log4j.xml");
	}

	@Override
	public void run() {
		LOGGER.debug("Service is running...");
	}
}