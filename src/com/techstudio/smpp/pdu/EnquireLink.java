package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class EnquireLink implements DefaultSetting{	
	int seq ;
	int status;
	
	public EnquireLink(){} // use with getBytes
	
	public EnquireLink(byte[] byteleft){	// use with getSequnceNo
		ByteBuffer buffer = new ByteBuffer(byteleft);
		status = buffer.removeInt();
		seq = buffer.removeInt();
	}
	
	public int getSequenceNo(){
		return seq;
	}
	
	public byte[] getBytes(int seq){
		byte[] outputbyte = null;
		try{
			PDUHeader header = new PDUHeader(ENQUIRE_LINK, NULL_BYTE, seq);
			outputbyte = header.getBytes(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return outputbyte;
	}
}

