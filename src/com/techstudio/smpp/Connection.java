package com.techstudio.smpp;

import java.net.*;
import java.io.*;
import java.util.*;
import org.apache.log4j.*;
import com.techstudio.smpp.util.*;

public class Connection extends Thread{
	Logger logger = null;

	protected String ip;
	protected int port;
	protected Socket socket;
	
	protected InputStream reader;
	protected OutputStream writer;
	protected boolean isReader = false;
	protected boolean isWriter = false;
	
	private Vector outputQueue;
	private Vector inputQueue;

	protected SMSReceiver receiver;
	protected SMSSender sender;

	protected int state = -1;
	
	protected boolean receiverAlive		= false;
	protected boolean senderAlive			= false;
	protected boolean connectionAlive = false;
	
	protected boolean reconnect			= false;
	
	protected Object inQLock 			= new Object();
	protected Object outQLock 		= new Object();
	protected String sendername		= "sender";
	protected String receivername	= "receiver";
	protected String name					= "";
	
	public Connection(String ip, int port, String aname, Logger logger){
		super(aname);
		this.name = aname;
		this.logger = logger;
		this.sendername	= name+":"+sendername;
		this.receivername	= name+":"+receivername;
		this.ip		= ip;
		this.port	= port;		
	}

	public synchronized boolean connect(){
		try{
			socket 		= new Socket(ip, port);
			if (socket!=null ){
				logger.info("SUCCESSFUL to open socket on ip ("+ip+") port ("+port+")");
				reader 			= socket.getInputStream();
				writer 			= socket.getOutputStream();
	
				outputQueue = new Vector();
				inputQueue 	= new Vector();
				
				isReader 	= true;
				isWriter 	= true;
	
				try{
					receiver 	= new SMSReceiver(this, receivername, logger);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				sender 		= new SMSSender(this, sendername, logger);
	
				reconnect	= false;
			}else{
				logger.info("FAIL to open socket on ip ("+ip+") port ("+port+")");
				return false;
			}
			return true;
		}catch(Exception e){
			logger.error("Exception on Connect:"+e);
			e.printStackTrace();
		}
		return false;
	}

	public synchronized void reconnect(String who){
		try{
			logger.info("*****************************************************************");
			logger.info("CAUSE RECONNECT >>" + (new Date()));
			logger.info("CAUSE RECONNECT >>" + who);
			logger.info("*****************************************************************");
			reconnect = true;
				
			isWriter			= false;
			isReader			= false;
			disconnect();
			
			sender		= null;
			receiver 	= null;
			check();
		}catch(Exception e){}
		System.exit(1);
	}

	public synchronized boolean isConnected(){
		try{
			if ( socket==null )
				return false;
			return socket.getKeepAlive();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public synchronized Object getInputMsg(){
		try{
			synchronized (inQLock){
				if ( inputQueue.size()==0 ){
					return null;
				}
				Object object =	inputQueue.firstElement();
				inputQueue.removeElementAt(0);
				return object;
			}
		}catch(Exception e){}
		return null;
	}
	
	public Object getOutputMsg(){
		try{
			synchronized (outQLock){
				if ( outputQueue.size()==0 )
					return null;
				Object object =	outputQueue.firstElement();
				outputQueue.removeElementAt(0);
				return object;
			}
		}catch(Exception e){}
		return null;
	}
	
	public void addOutputMsg(MTObj obj){
		try{
			synchronized (outQLock){
				outputQueue.addElement(obj);
				sender.unlock();		
			}
		}catch(Exception e){}
	}

	public void unlockSender(){
		try{
			sender.unlock();		
		}catch(Exception e){}
	}
	
	public void addInputMsg(MOObj obj){
		try{
			synchronized (inQLock){
				inputQueue.addElement(obj);
			}
		}catch(Exception e){}
	}
	
	public synchronized boolean isContSend(){
		senderAlive = true;
		return isReader;
	}

	public synchronized boolean isContReceive(){
		receiverAlive = true;
		return isWriter;
	}
	
	public synchronized void check(){
		senderAlive		= false;
		receiverAlive	= false;
		connectionAlive	= false;
	}
	
	public synchronized boolean isConnectionAlive(){
		if ( senderAlive&&receiverAlive&&connectionAlive )
			return true;
		return false;
	}
	
	public synchronized OutputStream getWriter(){
		return writer;
	}
	
	public synchronized InputStream getReader(){
		return reader;
	}
	
	public synchronized void disconnect(){
		logger.info("SMPP Connection disconnected");
		try{
			if ( receiver!=null )
				receiver.stop();
			receiver = null;
			if ( sender!=null )
				sender.stop();
			sender = null;
			if ( socket!=null )
			socket.close();
			socket = null;
			reset();
		}catch(Exception e){
			e.printStackTrace();
			socket = null;
			reset();
		}
	}
	
	public synchronized void reset(){
		try{
			check();
	    reader 		= null;
	    writer   	= null;
			
			outputQueue = null;
			inputQueue 	= null;
			
			sender		= null;
			receiver	= null;
		}catch(Exception e){}
	}
}

