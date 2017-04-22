package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

public class SubmitSMResp implements DefaultSetting{
	//Logger logger = ConnLogger.getLogger();

	int commandstatus;
	int seq;
	String messageid;
	
	public SubmitSMResp(byte[] data){	
		try{
			//printBytes(data, "SubmitSMResp");
			ByteBuffer bytebuffer = new ByteBuffer(data);

			commandstatus 	= bytebuffer.removeInt();
			seq  			= bytebuffer.removeInt();
			messageid = new String (bytebuffer.getBytes(), CHAR_ENC);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getCommandStatus(){
		return commandstatus;
	}
	
	public int getSequenceNo(){
		return seq;
	}
	
	public String getMessageID(){
		return messageid;
	}
	
	//public void printBytes(byte[] array, String name) {
	//	for (int k=0; k< array.length; k++) {
	//		 logger.info(name + "[" + k + "] = " + "0x" + com.techstudio.converter.UnicodeFormatter.byteToHex(array[k]));
	//	}
	//}	
}

