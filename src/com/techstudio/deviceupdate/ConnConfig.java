
package com.techstudio.deviceupdate;
import java.io.*;
import org.apache.log4j.*;
import java.text.SimpleDateFormat;
import java.util.*;

public final class ConnConfig
{
	public static String connfilename = "";
	private static Properties prop = new Properties();

	/*
	 * Method Name: reloadFile
	 * Input:void
	 * Returns: void
	 * Desc: To force ConnConfig reloads the data of properties file
	 * */	
	public static void reloadFile(){
		try { prop.load(new FileInputStream(connfilename)); }catch (IOException e){}
	}
	public static String getProperty(String key) { return prop.getProperty(key); }	
}
