package com.techstudio.smpp;

public interface CommandStatus{
	public final static String ESME_ROK 							= "00000000";
	public final static String ESME_ROK_DESC					= "No Error";
	
	public final static String ESME_RINVMSGLEN				= "00000001";
	public final static String ESME_RINVMSGLEN_DESC		= "Message Length is invalid";	
	
	public final static String ESME_RINVCMDLEN 				= "00000002";
	public final static String ESME_RINVCMDLEN_DESC		= "Command Length is invalid";
	
	public final static String ESME_RINVCMDID 				= "00000003";
	public final static String ESME_RINVCMDID_DESC		= "Invalid Command ID";
	/*
	public final static String ESME_RINVBNDSTS		= "00000000";
	public final static String ESME_ROK_DESC			= "Error";
	public final static String ESME_ROK 					= "00000000";
	public final static String ESME_ROK_DESC			= "Error";
	public final static String ESME_ROK 					= "00000000";
	public final static String ESME_ROK_DESC			= "Error";
	public final static String ESME_ROK 					= "00000000";
	public final static String ESME_ROK_DESC			= "Error";	
	public final static String ESME_ROK 					= "00000000";
	public final static String ESME_ROK_DESC			= "Error";
	public final static String ESME_ROK 					= "00000000";
	public final static String ESME_ROK_DESC			= "Error";	
	
	
	Incorrect BIND Status for given command
	ESME Alreay in Bound State
	Invalid Pririoty Flag
	Invalid Resgitered Delivery Flag
	System Error
	Reserved
	Invalid Source Address
	Invalid Dest Addr
	Message ID is invalid
	Bind Failed
	Invalid Password
	Invalid System ID
	Reserved
	Cancel SM Failed
	Replace SM Failed
	**/
}

