package com.techstudio.app;
import com.techstudio.app.controller.*;
import com.techstudio.mail.SendMail;
import com.techstudio.smpp.util.ConnConfig;
import com.techstudio.smpp.util.ConnLogger;
import com.techstudio.smpp.util.ConnLogger2;

import org.apache.log4j.*;

public class Start extends Thread implements SMPP_DefaultSetting{	
	Logger logger = ConnLogger.getLogger();

	ClientConnection transclientConn = null;
	ClientConnection recvclientConn = null;

	final int CHECK_TIME 	= 300000; //5 minutes
	final int REST_TIME 	= 600000; //10 minutes
	//DBHandler dbConn ;
	Object lock = new Object();
	Restart restart;
	String EMAILTO,EMAILFROM,EMAILHOST,EMAILINFO,EMAILTITLE;

	public static void main(String arg[]){

		System.out.println("arg:"+arg.length);
		if(arg != null && arg.length >= 1)
			new Start(arg[0]);
		else
			new Start();
	}

	public Start(){
		EMAILTO = ConnConfig.getProperty("EMAILTO");
		EMAILFROM = ConnConfig.getProperty("EMAILFROM");
		EMAILHOST = ConnConfig.getProperty("EMAILHOST");
		EMAILINFO = ConnConfig.getProperty("EMAILINFO");
		EMAILTITLE = ConnConfig.getProperty("EMAILTITLE");	
		init();
		start();
	}


	public Start(String confile){
		ConnConfig.reloadFile(confile);
		ConnLogger.reload();
		ConnLogger2.reload();
		logger = ConnLogger.getLogger();
		
		EMAILTO = ConnConfig.getProperty("EMAILTO");
		EMAILFROM = ConnConfig.getProperty("EMAILFROM");
		EMAILHOST = ConnConfig.getProperty("EMAILHOST");
		EMAILINFO = ConnConfig.getProperty("EMAILINFO");
		EMAILTITLE = ConnConfig.getProperty("EMAILTITLE");	
		init();
		start();
	}


	public void run(){
		try{
			synchronized(lock){
				lock.wait();
			}
		}catch(Exception e){}
		System.exit(1);
	}

	public void init(){
		try{
			logger.info("\n"+(new java.util.Date())+" ******************************* ");
			logger.info("\n"+(new java.util.Date())+" **** TRY CONNECT TO SERVER **** ");
			logger.info("\n"+(new java.util.Date())+" ******************************* ");
			String 	ip 		= ConnConfig.getProperty("SMPP.SERVERIP");
			int 	port 	= Integer.parseInt(ConnConfig.getProperty("SMPP.SERVERPORT"));
			String type 		= ConnConfig.getProperty("SMPP.TYPE");

			//dbConn = new DBHandler();
			//if ( dbConn.isConnected()==false )
			//	reinit();

			if ( type.equals("MO") || type.equals("BOTH") )
				recvclientConn = new ClientConnection(this, ip, port, STATE_BINDRECEIVER, "R", ConnLogger2.getLogger());

			if ( type.equals("MT") || type.equals("BOTH") )			
				transclientConn = new ClientConnection(this, ip, port, STATE_BINDTRANSMITTER, "T", ConnLogger.getLogger());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void restartTransmitter(){
		try{
			SendMail shtml = new SendMail();
			shtml.sendhtmlmail(EMAILTO,EMAILFROM,EMAILHOST,"",EMAILINFO+"Restart transmitter",
					EMAILTITLE);
		}catch(Exception e2){}	
		try{
			String type 		= ConnConfig.getProperty("SMPP.TYPE");
			if ( type.equals("MT") || type.equals("BOTH") ){
				if ( transclientConn!=null )
					transclientConn.disconnect();

				transclientConn = null;
				String 	ip 		= ConnConfig.getProperty("SMPP.SERVERIP");
				int 	port 	= Integer.parseInt(ConnConfig.getProperty("SMPP.SERVERPORT"));
				transclientConn = new ClientConnection(this, ip, port, STATE_BINDTRANSMITTER,"T", ConnLogger.getLogger());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void restartReceiver(){
		try{
			SendMail shtml = new SendMail();
			shtml.sendhtmlmail(EMAILTO,EMAILFROM,EMAILHOST,"",EMAILINFO+"Restart receiver",
					EMAILTITLE);
		}catch(Exception e2){}		
		try{
			String type 		= ConnConfig.getProperty("SMPP.TYPE");
			if ( type.equals("MO") || type.equals("BOTH") ){		
				if ( recvclientConn!=null )
					recvclientConn.disconnect();
				recvclientConn = null;
				String 	ip 		= ConnConfig.getProperty("SMPP.SERVERIP");
				int 	port 	= Integer.parseInt(ConnConfig.getProperty("SMPP.SERVERPORT"));
				recvclientConn = new ClientConnection(this, ip, port, STATE_BINDRECEIVER,"R", ConnLogger2.getLogger());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public synchronized void reinit(){	
		try{
			if ( transclientConn!=null )
				transclientConn.disconnect();
			transclientConn = null;


			if ( recvclientConn!=null )
				recvclientConn.disconnect();
			recvclientConn = null;

			//dbConn = null;
			restart = new Restart(this);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void unlock(){
		try{
			synchronized(lock){
				lock.notify();
			}
		}catch(Exception e){}
	}

	public void exit(){
		try{
			transclientConn = null;
			recvclientConn = null;
			//dbConn=null;
		}catch(Exception e){}
		System.exit(1);
	}	
}
