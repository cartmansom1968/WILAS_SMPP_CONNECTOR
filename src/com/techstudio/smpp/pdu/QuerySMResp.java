package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class QuerySMResp implements DefaultSetting
{
	int commandstatus;
	int seq;
	String messageid;
	String finaldate;
	int messagestate;
	int errorcode;
	
	public QuerySMResp(byte[] data){	
		try{
			ByteBuffer bytebuffer = new ByteBuffer(data);

			commandstatus 	= bytebuffer.removeInt();
			seq  						= bytebuffer.removeInt();
			messageid 			= new String (bytebuffer.getBytes(), CHAR_ENC);
			finaldate				= new String (bytebuffer.getBytes(), CHAR_ENC);
			messagestate		= bytebuffer.removeInt();
			errorcode 			= bytebuffer.removeInt();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public int getSequenceNo(){
		return seq;
	}	
	public int getCommandStatus(){
		return commandstatus;
	}
	
	public String getMessageID(){
		return messageid;
	}
	
	public String getFinalDate(){
		return finaldate;
	}

	public int getMessageState(){
		return messagestate;
	}

	public int getErrorCode(){
		return errorcode;
	}

}

