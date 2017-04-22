package com.techstudio.app;

public interface SMPP_DefaultSetting{
	public final static int TYPE_BIND_RECEIVER		= 1;
	public final static int TYPE_BIND_TRANSMITTER	= 2;

	public final static int STATE_BINDTRANSMITTER		= 0;
	public final static int STATE_BINDRECEIVER			= 1;
	public final static int STATE_WAITINGINITRESP		= 2;
	public final static int STATE_CONNECTASRECEIVER		= 3;
	public final static int STATE_CONNECTASTRANSMITTER	= 4;
	public final static int STATE_SMSCDOWN				= 5;
	
	public final static String[] statestr				= 
	{"BIND TRANSMITTER STATE", 
		"BIND RECEIVER STATE", 
		"WAITING RESPONSE STATE",
		"CONNECT AS RECEIVER STATE", 
		"CONNECT AS TRANSMITTER STATE", 
	"SMSC DOWN STATE"};

	public final static int RECEIVER_SLEEPTIME		= 300;
	public final static int SENDER_SLEEPTIME		= 300;
	public final static int CONN_SLEEPTIME			= 300;

	public final static int FLAG_INIT				= 0;
	public final static int FLAG_SEND				= 1;
	public final static int FLAG_QUERY				= 2;
	public final static int FLAG_OK					= 3;
	public final static int FLAG_FAIL				= 4;

	public final static String DL_LOGO				= "OTB";
	public final static String DL_PICTURE			= "OTA";
	public final static String DL_RINGTONE			= "OTT";

	public final static int BINDSTATUS_INIT				= -1;
	public final static int BINDSTATUS_BINDED			= 0;
	public final static int BINDSTATUS_BINDTRANSMITTER	= 1;
	public final static int BINDSTATUS_BINDRECEIVER		= 2;
}

