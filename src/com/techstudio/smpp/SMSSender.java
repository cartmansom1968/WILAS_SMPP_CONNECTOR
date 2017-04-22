package com.techstudio.smpp;
import java.io.*;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

public class SMSSender extends Thread{
	Logger logger = null;

	OutputStream writer;
	Connection connection;
	Object lock = new Object();
	String name = "SMSSender";
	
	public SMSSender(Connection connection, String aname, Logger logger){
		super(aname);
		this.name		= aname;
		this.logger	 	= logger;
		
		this.writer 	= connection.getWriter();
		this.connection	= connection;
		start();
	}

	public void run(){
		while (connection.isContSend()){
			try{
				do{
					//System.out.println("SMSSender 1");
					Object o = connection.getOutputMsg();
					//System.out.println("SMSSender 2");
					if ( o!=null ){
						//System.out.println("SMSSender 3");
						MTObj objectSend = (MTObj)o;
						byte[] sendMsg = objectSend.getBytes();
						String objname = objectSend.getName();
						if ( sendMsg!=null ){
							//System.out.println("SMSSender 4");
							//printBytes(sendMsg, name+":"+objname);
                            //System.out.println("------>"+sendMsg);
							writer.write(sendMsg);
							logger.info("<<-- SEND one msg - length ("+sendMsg.length+")..");
							writer.flush();
						}
						//System.out.println("SMSSender 5");
					}
					else
						break;
				}while(true);
				//System.out.println("SMSSender 6");
				try{
					synchronized (lock){
						lock.wait();
					}
				}catch(Exception e){}
				//System.out.println("SMSSender 7");
								
			}catch(Exception e){
				e.printStackTrace();
				connection.reconnect(name);
				break;
			}	
		}
		logger.info("************* EXIT SMSSender *************");
		if ( connection!=null )
			connection.reconnect("SMSSender");
		else
			System.exit(1);		
	}
	
	public void unlock(){
		try{
			synchronized(lock){
				lock.notify();
			}
		}
		catch(Exception e){}
	}
	
	public void printBytes(byte[] array, String name) {
		for (int k=0; k< array.length; k++) {
			 logger.info(name + "[" + k + "] = " + "0x" + com.techstudio.converter.UnicodeFormatter.byteToHex(array[k]));
		}
	}
}

