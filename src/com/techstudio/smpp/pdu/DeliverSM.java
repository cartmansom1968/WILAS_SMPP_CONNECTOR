package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class DeliverSM implements DefaultSetting{
	final int POS_SEQNO 		= 4;
	final int POS_SERVICETYPE 	= 8;
	int seqno;
	String servicetype;
	byte sourceaddrton;
	byte sourceaddrnpi;
	String sourceaddr;
	byte destaddrton;
	byte destaddrnpi;
	String destaddr;
	byte esmclass;
	byte protocolid;
	byte protocolflag;
	String scheduledeliverytime;	//unused for deliversm
	String validityperiod;		//unused for deliversm
	byte registereddelivery;
	byte replaceifpresentflag;	//unused for deliversm
	byte datacoding;
	byte smdefaultmsgid; 		//unused for deliversm
	byte smlength;
	byte[] message;
	int seq;
	String messageid;
	
	public DeliverSM(byte[] data){	
		try{
			
			//Debug.printBytes(data, "DeliverSM");	
			ByteBuffer bytebuffer = new ByteBuffer(data);
			int commandstatus 		= bytebuffer.removeInt(); 
			seqno 								= bytebuffer.removeInt();
			servicetype 					= bytebuffer.removeString();
			sourceaddrton 				= bytebuffer.removeByte();
			sourceaddrnpi 				= bytebuffer.removeByte();	
			sourceaddr 						= bytebuffer.removeString();
			destaddrton 					= bytebuffer.removeByte();
			destaddrnpi 					= bytebuffer.removeByte();
			destaddr 							= bytebuffer.removeString();
			esmclass 							= bytebuffer.removeByte();
			protocolid 						= bytebuffer.removeByte();
			protocolflag 					= bytebuffer.removeByte();
			scheduledeliverytime 	= bytebuffer.removeString();
			validityperiod 				= bytebuffer.removeString();
			registereddelivery 		= bytebuffer.removeByte();
			replaceifpresentflag 	= bytebuffer.removeByte();
			datacoding 						= bytebuffer.removeByte();	
			smdefaultmsgid 				= bytebuffer.removeByte();
			smlength 							= bytebuffer.removeByte();
			message 							= bytebuffer.getBytes();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getSequenceNo(){
		return seqno;
	}
	
	public byte getDataCoding(){
		return datacoding;
	}
	
	public byte getESMClass(){
		return esmclass;
	}
	
	public byte getMessageID(){
		return smdefaultmsgid;
	}	
	
	public byte[] getMessage(){
		return message;
	}	
	
	public String getSourceAddr(){
		return sourceaddr;
	}
	
	public String getDestAddr(){
		return destaddr;
	}
}

