package com.techstudio.smpp;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

import java.io.*;

public class SMSReceiver extends Thread implements DefaultSetting{
	Logger logger = null;

	InputStream reader;
	Connection connection;
	String name = "SMSReceiver";
	
	public SMSReceiver(Connection connection, String aname, Logger logger){
		super(aname);
		this.name	 	= aname;
		this.logger	 	= logger;
		
		this.reader 	= connection.getReader();
		this.connection	= connection;
		start();
	}

	public void run(){
		int length = 0;
		while (connection.isContReceive()){
			try{	
				byte[] receiveBuffer = new byte[4];
				for ( int i=0; i<SZ_INT; i++ ){
					int input = reader.read(); 
					while (input==-1 )
						input = reader.read(); 
					//logger.info("in ("+i+") = "+input);
					receiveBuffer[i] = (byte)input;
				}
				printBytes(receiveBuffer, name);
				ByteBuffer buffer = new ByteBuffer(receiveBuffer);
				int packetlength = buffer.removeInt();
				packetlength = packetlength - 4;
				if ( packetlength>0 ){	
					int bytesToRead = reader.available();
					byte[] tempbuffer = new byte[packetlength];
					for ( int i=0; i<packetlength; i++ ){
						int input = reader.read();
						tempbuffer[i] = (byte)input; 
					}
					buffer.add(tempbuffer);	
					logger.info("-->> RECEIVE packet length == "+packetlength);
	
					connection.addInputMsg((new MOObj(tempbuffer)));	
				}
			}catch(Exception e){
				e.printStackTrace();
				connection.reconnect(name);
				break;
			}
		}
		logger.info("************ EXIT SMSReceiver ************");
		if ( connection!=null )
			connection.reconnect("SMSReceiver");
		else
			System.exit(1);
	}

	public void printBytes(byte[] array, String name) {
		for (int k=0; k< array.length; k++) {
			 //logger.info(name + "[" + k + "] = " + "0x" + com.techstudio.converter.UnicodeFormatter.byteToHex(array[k]));
		}
	}
}

