package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class Unbind implements DefaultSetting{
	private String systemid = "";
	private String password = "";
	private String systemtype = "";
  private byte interfaceversion = 0;
  private byte addrton = 0;
  private byte addrnpi = 0;
	private String addressrange = "";
	byte zero	= 0;

	public Unbind(){}

	public byte[] getBytes(int seq){
		byte[] outputbyte = null;
		try{
			PDUHeader header = new PDUHeader(UNBIND, NULL_BYTE, seq);
			outputbyte = header.getBytes(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return outputbyte;
	}
}

