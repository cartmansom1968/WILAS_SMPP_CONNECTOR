package com.techstudio.smpp; 

public class MTObj{
	byte[] mtmsg;
	String name = "";
	
	public MTObj(byte[] msg, String name){
		this(msg);
		this.name = name;
	}
	
	public MTObj(byte[] msg){
		mtmsg = msg;
	}
	
	public String getName(){
		return name;
	}
	
	public byte[] getBytes(){
		return mtmsg;
	}
}
