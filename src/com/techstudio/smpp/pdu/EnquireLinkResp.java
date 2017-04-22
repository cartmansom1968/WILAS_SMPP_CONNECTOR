package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class EnquireLinkResp implements DefaultSetting{
	int enquirelinkseq;
	int commandstatus;
	int seq;
	String messageid;
	
	public EnquireLinkResp(){	
	}
	
	public byte[] getBytes(int enquirelinkseq){
		int length = 16;
		ByteBuffer bytebuffer = new ByteBuffer(length);
		bytebuffer.add(ENQUIRE_LINK_RESP);
		bytebuffer.add(ESME_ROK);
		bytebuffer.add(enquirelinkseq);
		return bytebuffer.getBytes();
	}
}

