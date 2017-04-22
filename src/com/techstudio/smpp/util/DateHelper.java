package com.techstudio.smpp.util;
import java.util.*;
import java.sql.Timestamp;
import java.text.*;

public class DateHelper
{
	public static String FORMAT_YEAR = "yyyy";
	public static String FORMAT_MONTH = "MM";
	public static String FORMAT_DAY = "dd";
	public static String FORMAT_HOUR = "HH";
	public static String FORMAT_MINUTE = "mm";
	public static String FORMAT_SECOND = "ss";

	public static String TIMEFORMAT_SQL=FORMAT_HOUR+":"+FORMAT_MINUTE+":"+FORMAT_SECOND;
	public static String DATEFORMAT_SQL=FORMAT_YEAR+"-"+FORMAT_MONTH+"-"+FORMAT_DAY+" "+TIMEFORMAT_SQL;

	public static long getDateTime()
	{
		try {
			Calendar rightNow = Calendar.getInstance();
			return rightNow.getTimeInMillis();
		}
		catch (Exception e){}
		return 0;
	}
	
	public synchronized static String getTimeNow(String format)
	{
		try 
		{
			Calendar rightNow = Calendar.getInstance();
			long time =  rightNow.getTimeInMillis();
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(time);
		}
		catch (Exception e){}
		return "";
	}
	public static String getDateFormat(long time, String format)
	{
		try 
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(time);
		}
		catch (Exception e){}
		return "";
	}	
	
	public static long getDate(String strdate, String format){
		try{
			int startindex=0;
			
			int[] datearr = new int[6];
			startindex = format.indexOf(FORMAT_YEAR);
			if ( startindex<0 )
				return -1;
			datearr[0] = Integer.parseInt(strdate.substring(startindex, startindex+FORMAT_YEAR.length()));
			
			startindex = format.indexOf(FORMAT_MONTH);
			if ( startindex<0 )
				return -1;
			datearr[1] = Integer.parseInt(strdate.substring(startindex, startindex+FORMAT_MONTH.length()));

			startindex = format.indexOf(FORMAT_DAY);
			if ( startindex<0 )
				return -1;
			datearr[2] = Integer.parseInt(strdate.substring(startindex, startindex+FORMAT_DAY.length()));

			startindex = format.indexOf(FORMAT_HOUR);
			if ( startindex<0 )
				return -1;
			datearr[3] = Integer.parseInt(strdate.substring(startindex, startindex+FORMAT_HOUR.length()));

			startindex = format.indexOf(FORMAT_MINUTE);
			if ( startindex<0 )
				return -1;
			datearr[4] = Integer.parseInt(strdate.substring(startindex, startindex+FORMAT_MINUTE.length()));

			startindex = format.indexOf(FORMAT_SECOND);
			if ( startindex<0 )
				return -1;
			datearr[5] = Integer.parseInt(strdate.substring(startindex, startindex+FORMAT_SECOND.length()));

			Calendar rightNow = Calendar.getInstance();
			rightNow.set(Calendar.YEAR, datearr[0]);
			rightNow.set(Calendar.MONTH, datearr[1]-1);
			rightNow.set(Calendar.DATE, datearr[2]);
			rightNow.set(Calendar.HOUR_OF_DAY, datearr[3]);
			rightNow.set(Calendar.MINUTE, datearr[4]);
			rightNow.set(Calendar.SECOND, datearr[5]);
			return rightNow.getTimeInMillis();
		}catch(Exception e){}
		return -1;
	}

	//HH:mm:ss
	public static int[] getTime(String strtime, String format){
		try{
			int startindex=0;
			int[] timearr = new int[3];
			startindex = format.indexOf(FORMAT_HOUR);
			if ( startindex<0 )
				return null;
			timearr[0] = Integer.parseInt(strtime.substring(startindex, startindex+FORMAT_HOUR.length()));

			startindex = format.indexOf(FORMAT_MINUTE);
			if ( startindex<0 )
				return null;
			timearr[1] = Integer.parseInt(strtime.substring(startindex, startindex+FORMAT_MINUTE.length()));

			startindex = format.indexOf(FORMAT_SECOND);
			if ( startindex<0 )
				return null;
			timearr[2] = Integer.parseInt(strtime.substring(startindex, startindex+FORMAT_SECOND.length()));
			
			return timearr;
		}catch(Exception e){}
		return null;
	}
	
	public static Timestamp parseTimestamp(String str)
	{
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(str);
			Timestamp timeSt = new Timestamp(date.getTime());
			return timeSt;
		}catch(Exception e){
			return null;
		}
	}
	
	public static Date parseDate(String str)
	{
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(str);
			return date;
		}catch(Exception e){
			return null;
		}
	}

	public static java.sql.Time parseTime(String str)
	{
		try{
			java.sql.Time time= java.sql.Time.valueOf(str);
			return time;
		}catch(Exception e){
			return null;
		}
	}


}

