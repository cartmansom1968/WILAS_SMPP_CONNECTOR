package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class DeliverSMResp implements DefaultSetting{
	int deliversmseq;
	int commandstatus;
	int seq;
	String messageid;
	
	public DeliverSMResp(int deliversmseq){	
		this.deliversmseq = deliversmseq;
	}
	
	public byte[] getBytes(int seq){
		int length = 17;
		ByteBuffer bytebuffer = new ByteBuffer(length);
		bytebuffer.add(DELIVER_SM_RESP);
		bytebuffer.add(ESME_ROK);
		bytebuffer.add(deliversmseq);
		bytebuffer.add(NULL_BYTE);
		return bytebuffer.getBytes();
	}
}

