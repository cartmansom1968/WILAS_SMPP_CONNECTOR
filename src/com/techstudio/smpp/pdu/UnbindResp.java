package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class UnbindResp implements DefaultSetting{
	int commandstatus;
	int seq;
	public UnbindResp(byte[] data){	
		try{
			ByteBuffer bytebuffer = new ByteBuffer(data);

			commandstatus 	= bytebuffer.removeInt();
			seq  			= bytebuffer.removeInt();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getCommandStatus(){
		return commandstatus;
	}
}

