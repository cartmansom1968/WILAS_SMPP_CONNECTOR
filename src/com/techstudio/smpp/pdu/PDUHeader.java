package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

public class PDUHeader implements DefaultSetting{
	//Logger logger = ConnLogger.getLogger();

	private int commandLength 	= 0;
  private int commandId 			= 0;
  private int commandStatus 	= 0;
  private int sequenceNumber 	= 1;
	
	public PDUHeader(int commandid, int commandstatus, int sequencenumber){	
		commandId = commandid;
		commandStatus = commandstatus;
		sequenceNumber = sequencenumber;
	}

	public byte[] getBytes(byte[] data){
		if ( data==null ){
			commandLength = 16;
			ByteBuffer bytebuffer = new ByteBuffer(commandLength);
			bytebuffer.add(commandId);
			bytebuffer.add(commandStatus);
			bytebuffer.add(sequenceNumber);
			return bytebuffer.getBytes();
		}
		
		commandLength = data.length + 16;
		//logger.info("PDUHeader > commandLength  > "+commandLength);
		//logger.info("PDUHeader > commandId      > "+commandId);
		//logger.info("PDUHeader > commandStatus  > "+commandStatus);
		//logger.info("PDUHeader > sequenceNumber > "+sequenceNumber);
		ByteBuffer bytebuffer = new ByteBuffer(commandLength);
		bytebuffer.add(commandId);
		bytebuffer.add(commandStatus);
		bytebuffer.add(sequenceNumber);
		bytebuffer.add(data);
		return bytebuffer.getBytes();
	}
}

