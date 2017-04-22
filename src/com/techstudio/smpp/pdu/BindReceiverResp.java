package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class BindReceiverResp implements DefaultSetting{
	int commandstatus;
	int seq;
	String systemid;
	
	public BindReceiverResp(byte[] data){	
		try{
			ByteBuffer bytebuffer = new ByteBuffer(data);

			commandstatus 	= bytebuffer.removeInt();
			seq  						= bytebuffer.removeInt();
			systemid 				= new String (bytebuffer.getBytes(), CHAR_ENC);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getCommandStatus(){
		return commandstatus;
	}
	
	public String getSystemID(){
		return systemid;
	}
}

