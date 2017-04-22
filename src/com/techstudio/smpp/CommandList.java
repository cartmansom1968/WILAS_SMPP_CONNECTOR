package com.techstudio.smpp;

public interface CommandList{

	public final static String GENERIC_NACK 					= "80000000";
	public final static String BIND_RECEIVER 					= "00000001";
	public final static String BIND_RECEIVER_RESP 		= "80000001";
	public final static String BIND_TRANSMITTER				= "00000002";
	public final static String BIND_TRANSMITTER_RESP	= "80000002";
	public final static String QUERY_SM	 							= "00000003";
	public final static String QUERY_SM_RESP 					= "80000003";
	public final static String SUMMIT_SM	 						= "00000004";
	public final static String SUMMIT_SM_RESP	 				= "80000004";
	public final static String DELIVER_SM 						= "00000005";
	public final static String DELIVER_SM_RESP 				= "80000005";
	public final static String UNBIND	 								= "00000006";
	public final static String UNBIND_RESP	 					= "80000006";
	public final static String REPLACE_SM	 						= "00000007";
	public final static String REPLACE_SM_RESP	 			= "80000007";
	public final static String CANCEL_SM 							= "00000008";
	public final static String CANCEL_SM_RESP 				= "80000008";
	public final static String BIND_TRANSCEIVER				= "00000009";
	public final static String BIND_TRANSCEIVER_RESP	= "80000009";
	public final static String OUTBIND		 						= "0000000B";
	public final static String ENQUIRE_LINK		 				= "00000015";
	public final static String ENQUIRE_LINK_RESP			= "80000015";
	public final static String SUMMIT_MULTI	 					= "00000021";
	public final static String SUMMIT_MULTI_RESP 			= "80000021";
	public final static String ALERT_NOTIFICATION 		= "00000102";
	public final static String DATA_SM	 							= "00000021";
	public final static String DATA_SM_RESP 					= "80000021";
}

