package com.techstudio.smpp; 

public class MOObj{

	byte[] obj;
	public MOObj(byte[] msg){
		obj = msg;
	}

	public byte[] getBytes(){
		return obj;
	}
}

