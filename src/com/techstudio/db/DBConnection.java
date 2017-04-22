package com.techstudio.db;
import org.apache.commons.dbcp.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import org.apache.log4j.*;
 
public class DBConnection {
	private static DBConnection dbConn = null;
	private BasicDataSource bds = null;
	boolean isClosedFlag=false;
	Object key = new Object();
	private Logger logger = null;
	DBObj obj = null;
	private DBConnection(DBObj obj, Logger logger){
		this.logger=logger;
		this.obj=obj;
		try{
			connect();
		}catch(Exception e){}	
	}
	
	public static DBConnection getInstance(DBObj obj, Logger logger){
		
		if ( dbConn==null )
			dbConn = new DBConnection(obj, logger);
		return dbConn;
	}
	
	private void connect() throws SQLException{
		if ( !isClosedFlag ){
			synchronized (key) {
				logger.info("LOAD|INIT|DATABASE|Loading Database..");
				logger.info("INFO|DATABASE|urldbpath="+obj.urldbpath);
				logger.info("INFO|DATABASE|driver="+obj.driver);
				logger.info("INFO|DATABASE|username="+obj.username);
				logger.info("INFO|DATABASE|password="+obj.password);
				logger.info("INFO|DATABASE|initialsize="+obj.initialsize);
				logger.info("INFO|DATABASE|maxsize="+obj.maxsize);
				
				bds = new BasicDataSource();
				bds.setDriverClassName(obj.driver);
				bds.setUsername(obj.username);
				bds.setPassword(obj.password);
				bds.setUrl(obj.urldbpath);
				bds.setInitialSize(obj.initialsize);
				bds.setMaxActive(obj.maxsize);
				logger.info("LOAD|SUCCESS|DATABASE|Successfully connect to database..");
			}
		}
	}	
	
	public void checkConnection (){		
		if ( isClosedFlag && isClosed() ){
			synchronized(key){
				if ( bds!=null ){
					try{ connect(); }catch(Exception ee){}
				}else
					try{ connect(); }catch(Exception ee){}
			}
		}
	}	
	
	public Connection getConnection(){
		try{
			synchronized(key){
				return bds.getConnection();
			}
		}catch(SQLException e){		
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isClosed(){
		if ( isClosedFlag )
			return isClosedFlag;
			
		synchronized(key){
			Connection con = null;
			try{
				con = bds.getConnection();
				return con.isClosed();
			}catch(Exception e){
				logger.info("INFO|ERROR|Database connection error: "+e.getMessage());		
				StackTraceElement strErr[] = e.getStackTrace();
	      for ( int i=0; i<strErr.length; i++ )
	      	logger.info("  "+strErr[i].toString());			
			}
			finally {
				if (con!=null) try { con.close(); } catch(Exception e) 	{}					
			}		
		}
		return true;
	}
	
	public void closeConnection() throws SQLException 
	{
		isClosedFlag=true;
		synchronized(key){
			if (bds != null){
					bds.close();
			}
		}
		bds = null;	
	}	
}
