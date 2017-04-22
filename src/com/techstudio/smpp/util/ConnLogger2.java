package com.techstudio.smpp.util;


import java.io.*;
import org.apache.log4j.*;
import java.text.SimpleDateFormat;
import java.util.*;

public final class ConnLogger2 {
	private static Logger myLogger;
	
	static{
		try{
			myLogger 	= initLogger(ConnConfig.getProperty("PROCESSID")+"2", ConnConfig.getProperty("LOGGERDIR"), 
															ConnConfig.getProperty("LOGMODE"));
			if ( myLogger==null )
				throw new Exception();
		}catch (Exception e){
			System.err.println("ConnLogger|" + e.getMessage());
			e.printStackTrace();
		}
	}
	public static void reload(){
		try{
			myLogger 	= initLogger(ConnConfig.getProperty("PROCESSID")+"2", ConnConfig.getProperty("LOGGERDIR"), 
															ConnConfig.getProperty("LOGMODE"));
			if ( myLogger==null )
				throw new Exception();
		}catch (Exception e){
			System.err.println("ConnLogger|" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static Logger initLogger(String ProcessID, String logdir, String LogMode){
		try{
			File f = new File(logdir);
			if ( f.exists() )
				f.createNewFile();
		}catch(IOException e){}
		try{
			Properties properties = new Properties();
			properties.setProperty("log4j.logger." + ProcessID, ", file");
			properties.setProperty("log4j.appender.file", "org.apache.log4j.DailyRollingFileAppender");
			properties.setProperty("log4j.appender.file.File", (new File(logdir, ProcessID + ".log")).getPath());
	    properties.setProperty("log4j.appender.file.Append", "true");
			properties.setProperty("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
			//properties.setProperty("log4j.appender.file.layout.ConversionPattern", "%d{yyyyMMdd|HHmmss:SSS}|%m%n");
			properties.setProperty("log4j.appender.file.layout.ConversionPattern", "%d %-p [%t] - %m%n");
			PropertyConfigurator.configure(properties);	

			Logger myLogger = Logger.getLogger(ProcessID);
			if (LogMode.equalsIgnoreCase("DEBUG"))
				myLogger.setLevel((Level) Level.DEBUG);
			else if (LogMode.equalsIgnoreCase("INFO"))
				myLogger.setLevel((Level) Level.INFO);
			else if (LogMode.equalsIgnoreCase("WARN"))
				myLogger.setLevel((Level) Level.WARN);
			else if (LogMode.equalsIgnoreCase("ERROR"))
				myLogger.setLevel((Level) Level.ERROR);
			else if (LogMode.equalsIgnoreCase("FATAL"))
				myLogger.setLevel((Level) Level.FATAL);
			else if (LogMode.equalsIgnoreCase("ALL"))
				myLogger.setLevel((Level) Level.ALL);
			else if (LogMode.equalsIgnoreCase("OFF"))
				myLogger.setLevel((Level) Level.OFF);
			
			return myLogger;
		}catch(Exception e){}
		return null;
	}

	public static Logger getLogger(){
		return myLogger;
	}
}
