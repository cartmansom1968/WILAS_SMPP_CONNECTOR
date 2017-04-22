package com.techstudio.app.entities;
import com.techstudio.converter.*;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

public class ReceiveObj implements com.techstudio.smpp.DefaultSetting{
	Logger logger = ConnLogger.getLogger();

	long msgid = 0;
	String sender = "";
	String message = "";
	int encoding = 1;
	long shortcode = 0;
	
	public ReceiveObj(String sender, byte[] bytemessage, byte encoding){
		this.sender = sender;
		if ( encoding==DATAENCODING_UCS2 ){
			this.encoding=3;
			try{
				message		= UnicodeFormatter.bytesToHex(bytemessage);
			}catch(Exception e){e.printStackTrace();}
		}
		else if ( encoding==DATAENCODING_ASCII ){
			this.encoding=1;
			this.message 	= new String(bytemessage);
		}
		else{	
			logger.info("INKNOWN Encoding >> "+encoding+".");
		}
	}
	
	public void setShortCode(long shortcode){
		this.shortcode=shortcode;
	}
	
	
	public long getShortCode(){
		return shortcode;
	}
	
	public String getSender(){
		return sender;
	}
	
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message = message;
	}
	
	public int getEncoding(){
		return encoding;
	}

	public static void printBytes(byte[] data, String who){
		Logger logger = ConnLogger.getLogger();

		for ( int i=0; i<data.length; i++ ){
			logger.info(who+"["+i+"] = "+data[i]);
		}
	}
}

/**
	
	public static void main(String arg[]){
		byte[] DeliverSMbyte = new byte[79];
		DeliverSMbyte[0] = 0;
DeliverSMbyte[1] = 0;
DeliverSMbyte[2] = 0;
DeliverSMbyte[3] = 0;
DeliverSMbyte[4] = 0;
DeliverSMbyte[5] = 0;
DeliverSMbyte[6] = 0;
DeliverSMbyte[7] = 2;
DeliverSMbyte[8] = 0;
DeliverSMbyte[9] = 1;
DeliverSMbyte[10] = 1;
DeliverSMbyte[11] = 56;
DeliverSMbyte[12] = 53;
DeliverSMbyte[13] = 50;
DeliverSMbyte[14] = 57;
DeliverSMbyte[15] = 51;
DeliverSMbyte[16] = 48;
DeliverSMbyte[17] = 55;
DeliverSMbyte[18] = 48;
DeliverSMbyte[19] = 56;
DeliverSMbyte[20] = 52;
DeliverSMbyte[21] = 55;
DeliverSMbyte[22] = 0;
DeliverSMbyte[23] = 0;
DeliverSMbyte[24] = 0;
DeliverSMbyte[25] = 53;
DeliverSMbyte[26] = 48;
DeliverSMbyte[27] = 50;
DeliverSMbyte[28] = 52;
DeliverSMbyte[29] = 55;
DeliverSMbyte[30] = 49;
DeliverSMbyte[31] = 48;
DeliverSMbyte[32] = 0;
DeliverSMbyte[33] = 0;
DeliverSMbyte[34] = 0;
DeliverSMbyte[35] = 0;
DeliverSMbyte[36] = 0;
DeliverSMbyte[37] = 0;
DeliverSMbyte[38] = 0;
DeliverSMbyte[39] = 0;
DeliverSMbyte[40] = 8;
DeliverSMbyte[41] = 0;
DeliverSMbyte[42] = 36;
DeliverSMbyte[43] = -118;
DeliverSMbyte[44] = -53;
DeliverSMbyte[45] = 98;
DeliverSMbyte[46] = 83;
DeliverSMbyte[47] = 98;
DeliverSMbyte[48] = 17;
DeliverSMbyte[49] = 81;
DeliverSMbyte[50] = 108;
DeliverSMbyte[51] = 83;
DeliverSMbyte[52] = -8;
DeliverSMbyte[53] = -106;
DeliverSMbyte[54] = -5;
DeliverSMbyte[55] = -118;
DeliverSMbyte[56] = 113;
DeliverSMbyte[57] = 48;
DeliverSMbyte[58] = 2;
DeliverSMbyte[59] = 0;
DeliverSMbyte[60] = 40;
DeliverSMbyte[61] = 0;
DeliverSMbyte[62] = 50;
DeliverSMbyte[63] = 0;
DeliverSMbyte[64] = 56;
DeliverSMbyte[65] = 0;
DeliverSMbyte[66] = 56;
DeliverSMbyte[67] = 0;
DeliverSMbyte[68] = 56;
DeliverSMbyte[69] = 0;
DeliverSMbyte[70] = 49;
DeliverSMbyte[71] = 0;
DeliverSMbyte[72] = 49;
DeliverSMbyte[73] = 0;
DeliverSMbyte[74] = 54;
DeliverSMbyte[75] = 0;
DeliverSMbyte[76] = 50;
DeliverSMbyte[77] = 0;
DeliverSMbyte[78] = 41;
/*
byte[] DeliverSMbyte = new byte[45];
DeliverSMbyte[0] = 0;
DeliverSMbyte[1] = 0;
DeliverSMbyte[2] = 0;
DeliverSMbyte[3] = 0;
DeliverSMbyte[4] = 0;
DeliverSMbyte[5] = 0;
DeliverSMbyte[6] = 0;
DeliverSMbyte[7] = 1;
DeliverSMbyte[8] = 0;
DeliverSMbyte[9] = 1;
DeliverSMbyte[10] = 1;
DeliverSMbyte[11] = 56;
DeliverSMbyte[12] = 53;
DeliverSMbyte[13] = 50;
DeliverSMbyte[14] = 57;
DeliverSMbyte[15] = 51;
DeliverSMbyte[16] = 48;
DeliverSMbyte[17] = 55;
DeliverSMbyte[18] = 48;
DeliverSMbyte[19] = 56;
DeliverSMbyte[20] = 52;
DeliverSMbyte[21] = 55;
DeliverSMbyte[22] = 0;
DeliverSMbyte[23] = 0;
DeliverSMbyte[24] = 0;
DeliverSMbyte[25] = 53;
DeliverSMbyte[26] = 48;
DeliverSMbyte[27] = 50;
DeliverSMbyte[28] = 52;
DeliverSMbyte[29] = 55;
DeliverSMbyte[30] = 49;
DeliverSMbyte[31] = 48;
DeliverSMbyte[32] = 0;
DeliverSMbyte[33] = 0;
DeliverSMbyte[34] = 0;
DeliverSMbyte[35] = 0;
DeliverSMbyte[36] = 0;
DeliverSMbyte[37] = 0;
DeliverSMbyte[38] = 0;
DeliverSMbyte[39] = 0;
DeliverSMbyte[40] = 0;
DeliverSMbyte[41] = 0;
DeliverSMbyte[42] = 2;
DeliverSMbyte[43] = 50;
DeliverSMbyte[44] = 98;
*/
/*
		com.techstudio.smpp.pdu.DeliverSM sm = new com.techstudio.smpp.pdu.DeliverSM(DeliverSMbyte);
		new ReceiveObj(sm.getSourceAddr(), sm.getMessage(), sm.getDataCoding());
	}
	
*/
