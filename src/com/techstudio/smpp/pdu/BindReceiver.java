package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class BindReceiver implements DefaultSetting{
	private String systemid = "";
	private String password = "";
	private String systemtype = "";
  private byte interfaceversion = 0;
  private byte addrton = 0;
  private byte addrnpi = 0;
	private String addressrange = "";
	byte zero	= 0;

	public BindReceiver(String systemid, String password , String systemtype, byte interfaceversion,
						byte addrton, byte addrnpi, String addressrange){	
		this.systemid = systemid;
		this.password = password;
		this.systemtype = systemtype;
		this.interfaceversion = interfaceversion;
		this.addrton = addrton;
		this.addrnpi = addrnpi;
		this.addressrange = addressrange;
	}

	public byte[] getBytes(int seq){
		byte[] outputbyte = null;
		try{
			ByteBuffer bytebuffer = new ByteBuffer(systemid);							
			bytebuffer.add(password);
			bytebuffer.add(systemtype);
			bytebuffer.add(interfaceversion);
			bytebuffer.add(addrton);
			bytebuffer.add(addrnpi);
			bytebuffer.add(addressrange);

			PDUHeader header = new PDUHeader(BIND_RECEIVER, NULL_BYTE, seq);
			outputbyte = header.getBytes(bytebuffer.getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}
		return outputbyte;
	}
}

