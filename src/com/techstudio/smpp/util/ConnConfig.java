package com.techstudio.smpp.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public final class ConnConfig{

	private static String connfilename = "config/app.conf";
	private static Properties prop = new Properties();

	static{
		reloadFile();
	}
	/*
	 * Method Name: reloadFile
	 * Input:void
	 * Returns: void
	 * Desc: To force ConnConfig reloads the data of properties file
	 * */	
	public static void reloadFile(){
		try { prop.load(new FileInputStream(connfilename)); }catch (IOException e){e.printStackTrace();}
	}
	
	public static void reloadFile(String con){
		connfilename = con;
		try { prop.load(new FileInputStream(connfilename)); }catch (IOException e){e.printStackTrace();}
	}

	public static String getPROCESSID() { return prop.getProperty("PROCESSID"); }	
	public static String getLOGGERDIR() { return prop.getProperty("LOGGERDIR"); }	
	public static String getLOGMODE() { return prop.getProperty("LOGMODE"); }	

	public static String getProperty(String key) { return prop.getProperty(key); }	
}
