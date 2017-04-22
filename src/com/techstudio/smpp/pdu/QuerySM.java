package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

public class QuerySM implements DefaultSetting{
	//Logger logger = ConnLogger2.getLogger();
	private String messageid = "";
	private String soureaddr = "";
  private byte addrton = 0;
  private byte addrnpi = 0;
	byte zero	= 0;
	//QuerySM
	public QuerySM(String messageid, byte addrton, byte addrnpi, String soureaddr){	
		this.messageid	= messageid;
		this.addrton 		= addrton;
		this.addrnpi 		= addrnpi;
		this.soureaddr 	= soureaddr;
	}

	public byte[] getBytes(int seq){
		byte[] outputbyte = null;
		try{
			ByteBuffer bytebuffer = new ByteBuffer(messageid);							
			bytebuffer.add(addrton);
			bytebuffer.add(addrnpi);
			bytebuffer.add(soureaddr);

			//printBytes(bytebuffer.getBytes(), "QuerySM");
			
			PDUHeader header = new PDUHeader(QUERY_SM, NULL_BYTE, seq);
			outputbyte = header.getBytes(bytebuffer.getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}
		return outputbyte;
	}
	
	//public void printBytes(byte[] array, String name) {
	//	for (int k=0; k< array.length; k++) {
	//		 logger.info(name + "[" + k + "] = " + "0x" + com.techstudio.converter.UnicodeFormatter.byteToHex(array[k]));
	//	}
	//}
}

