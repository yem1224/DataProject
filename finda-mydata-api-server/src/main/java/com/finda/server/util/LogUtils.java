package com.finda.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LogUtils {
	protected static Logger mydataLogger = LoggerFactory.getLogger("mydata");
	
	public static void writeLog(String mydataLog) {
		try {
			mydataLogger.info(new ObjectMapper().writeValueAsString(mydataLog));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
