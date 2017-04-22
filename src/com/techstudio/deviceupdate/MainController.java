
package com.techstudio.deviceupdate;
import org.apache.log4j.*;
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;
import com.techstudio.db.*; 

public class MainController 
{
	private Logger logger = null;
	
	public MainController(String configfile){
		try{	
			ConnConfig.connfilename=configfile;
			ConnConfig.reloadFile();
			logger=ConnLogger.getLogger();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void start(){
		String dburl = ConnConfig.getProperty("db.url");
		String driver = ConnConfig.getProperty("db.driver");
		String username = ConnConfig.getProperty("db.username");
		String password = ConnConfig.getProperty("db.password");
		String initialsize = ConnConfig.getProperty("db.initialsize");
		String maxsize = ConnConfig.getProperty("db.maxsize");
		String dbtype = ConnConfig.getProperty("db.dbtype");
		
		try{
			DBObj dbobj = new DBObj (dburl, driver, username, password, Integer.parseInt(initialsize), Integer.parseInt(maxsize));
			DBManager dbmgr = DBManager.getInstance(dbobj, logger);
			
			String sql = "select lastupdatedid from tb_application";
			logger.info(sql);
			LinkedList list = dbmgr.getRecord(sql);
			if ( list!=null && list.size()>0){
				String[] arr = (String[])list.get(0);
				String strurl = "http://203.142.17.219/devicemgt/update/applicationupdate.jsp?applicationid=1&loginpwd=tss&lastupdatedid="+arr[0];
				logger.info("strurl:"+strurl);
				String retsql = getSQL(strurl);
				logger.info("retsql:"+retsql);
				StringTokenizer st = new StringTokenizer(retsql, "\n");
				while(st.hasMoreTokens()){
					String str = st.nextToken();
					int index = str.indexOf("|");
					if ( index>0 ){
						String id  = str.substring(0, index);
						String devicesql = str.substring(index+1, str.length());
						try{
							long retvalue = dbmgr.updateRecord(devicesql);
							logger.info("retvalue:"+retvalue+"."+devicesql);	
							
						}catch(Exception e){
							logger.error("fail to update."+devicesql);
						}
						dbmgr.updateRecord("update tb_application set lastupdatedid="+id);
						
					}
				}
			}
		}catch(Exception e){
			logger.error(e);	
		}
		
	}
	
	public static String getSQL(String strurl){
		try{
			URL url = new URL(strurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
			conn.setDoInput(true); 
			conn.setDoOutput(true);
			conn.setUseCaches (false);
			
			//application/soap+xml
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestMethod("GET");
			int respcode = conn.getResponseCode();
			System.out.println("respcode:"+respcode);
			
       int c = '\0';
       BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			 String finalstr = "";
       while ( (c = bis.read()) != -1 ) {  
          //System.out.print(c);
					finalstr=finalstr+(char)c;
       }// while.
			finalstr=finalstr.trim();	
			return finalstr;
		} catch (Exception e) { 
        e.printStackTrace(); 
    } 	
		return "";
	}		
}
