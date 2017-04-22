package com.techstudio.smpp.pdu;
import com.techstudio.smpp.util.*;
import com.techstudio.smpp.*;
import org.apache.log4j.*;

public class SubmitConcatSM implements DefaultSetting{
	//Logger logger = ConnLogger.getLogger();

	String servicetype;
	byte sourceaddrton;
	byte sourceaddrnpi;
	String sourceaddr;
	byte destaddrton;
	byte destaddrnpi;
	String destaddr;
	byte esmclass;
	byte protocolid;
	byte priotiryflag;
	String scheduledeliverytime;
	String validperiod;
	byte registereddeliveryflag;
	byte replace;
	byte dataencoding;
	byte msgid;
	short length;
	byte[] message;
	byte zero	= 0;
	short sar_msg_ref_num;
	byte sar_total_segments;
	byte sar_segment_seq_num;
	
	public SubmitConcatSM(String servicetype, byte sourceaddrton, byte sourceaddrnpi, String sourceaddr,
					byte destaddrton, byte destaddrnpi, String destaddr, byte esmclass,
					byte protocolid, byte priotiryflag, String scheduledeliverytime,
					String validperiod, byte registereddeliveryflag, byte replace, byte dataencoding,
					byte msgid, short length, byte[] message){
		this.servicetype 						= servicetype;
		this.sourceaddrton					= sourceaddrton;
		this.sourceaddrnpi					= sourceaddrnpi;
		this.sourceaddr							= sourceaddr;
		this.destaddrton						= destaddrton;
		this.destaddrnpi						= destaddrnpi;
		this.destaddr								= destaddr;
		this.esmclass								= esmclass;
		this.protocolid							= protocolid;
		this.priotiryflag						= priotiryflag;
		this.scheduledeliverytime		= scheduledeliverytime;
		this.validperiod						= validperiod;
		this.registereddeliveryflag	= registereddeliveryflag;
		this.replace								= replace;
		this.dataencoding						= dataencoding;
		this.msgid									= msgid;
		this.length									= length;
		this.message								= message;
		this.sar_msg_ref_num				= sar_msg_ref_num;
		this.sar_total_segments			= sar_total_segments;
		this.sar_segment_seq_num		= sar_segment_seq_num;
		
	}
	
	public byte[] getBytes(int seq){
		byte[] outputbyte = null;
		try{
			ByteBuffer bytebuffer = new ByteBuffer(servicetype);
			bytebuffer.add(sourceaddrton);
			bytebuffer.add(sourceaddrnpi);
			bytebuffer.add(sourceaddr);
			bytebuffer.add(destaddrton);
			bytebuffer.add(destaddrnpi);
			bytebuffer.add(destaddr);
			bytebuffer.add(esmclass);
			bytebuffer.add(protocolid);
			bytebuffer.add(priotiryflag);
			bytebuffer.add(scheduledeliverytime);
			bytebuffer.add(validperiod);
			bytebuffer.add(registereddeliveryflag);
			bytebuffer.add(replace);
			bytebuffer.add(dataencoding);
			bytebuffer.add(msgid);
			bytebuffer.add((byte)0);
			//bytebuffer.add(NULL_STRING);
/*
			bytebuffer.add(TAG_SARMSGREFNUM);
			bytebuffer.add((short)1);
			bytebuffer.add(sar_msg_ref_num);

			bytebuffer.add(TAG_SARSEGMENTSEQNUM);
			bytebuffer.add((short)1);
			bytebuffer.add(sar_segment_seq_num);
 
			bytebuffer.add(TAG_SARTOTALSEGMENTS);
			bytebuffer.add((short)1);
			bytebuffer.add(sar_total_segments);
*/
			bytebuffer.add(TAG_MESSAGEPAYLOAD);
			bytebuffer.add(length);
			bytebuffer.add(message);
			
			//printBytes(bytebuffer.getBytes(), "SubmitConcatSM");


			PDUHeader header = new PDUHeader(SUBMIT_SM, NULL_BYTE, seq);
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

