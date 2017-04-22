package com.techstudio.smpp.pdu;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;

public class DeliveryReceipt implements DefaultSetting{
	String messageid = "";
	String state = "";
	String err = "";
	
	public DeliveryReceipt(String data){	
		try{
			String[] arr = data.split(" ");
			for ( int i=0; i<arr.length; i++ ){
				String[] arr2 = arr[i].split(":");
				if (arr2[0].equals("id") ){
					messageid=arr2[1];
				}else if (arr2[0].equals("stat") ){
					state=arr2[1];
				}else if (arr2[0].equals("err")) {
					err=arr2[1];
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getMessageID(){
		return messageid;
	}
	public String getState(){
		return state;
	}	
	public String getError() { return err; }
}

