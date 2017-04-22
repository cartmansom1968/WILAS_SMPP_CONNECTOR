package com.techstudio.smpp;

public interface DefaultSetting
{
  public static final byte RECEIPTREQUIRED          = (byte)0x01; // 1

	public static final byte ESMCLASS_DEFAULT				  = (byte)0x0;
	public static final byte ESMCLASS_BINARY 					= (byte)0x40;

	public static final byte ESMCLASS_DR		 					= (byte)0x4;	
	public static final byte DATAENCODING_UCS2			  = (byte)0x8;
	public static final byte DATAENCODING_ASCII			  = (byte)0x0;
	public static final byte DATAENCODING_BINARY			= (byte)0xF5;	
  
	public static final byte FLAG_NOREPLACE           = (byte)0x00;
  public static final byte FLAG_REPLACE             = (byte)0x01;
	
	//service type
	public static final String SERVICETYPE_DEFAULT 	= ""; 	//default / NULL
	public static final String SERVICETYPE_CMT 			= "CMT";		//cellular message
	public static final String SERVICETYPE_CPT 			= "CPT";		//cellular paging
	public static final String SERVICETYPE_WMN 			= "WMN";		//voice mail notification
	public static final String SERVICETYPE_WMA 			= "WMA";		//voice mail alert
	public static final String SERVICETYPE_WAP 			= "WAP";		//wireless application protocol
	public static final String SERVICETYPE_USSD 		= "USSD";		//unstructured supplementary service data
	
	public static final int COMMAND_LEN					= 4;
	
	public static final byte NULL_BYTE  	   		= (byte)0x00;
	public static final int NULL_INT   	   		  = 0x00000000;
	public static final String NULL_STRING 		  = "";

  //public static final short TAG_SARMSGREFNUM     = (short)0x020C;
  //public static final short TAG_SARSEGMENTSEQNUM	= (short)0x020F;
  //public static final short TAG_SARTOTALSEGMENTS = (short)0x020E;
  public static final short TAG_MESSAGEPAYLOAD   = (short)0x0424;
			 
  //Interface_Version
  public static final byte SMPP_V00             = (byte)0x00;
	public static final byte SMPP_V33             = (byte)0x33;
  public static final byte SMPP_V34             = (byte)0x34;
 	public static final byte SMPP_V52             = (byte)0x52;
	
	//Type of Number - TON to be used in SME address parameter
	public static final byte TON_UNKNOWN					= (byte)0x00; // 0
	public static final byte TON_INTERNATIONAL		= (byte)0x01; // 1
	public static final byte TON_NATIONAL					= (byte)0x02; // 2
	public static final byte TON_NETWORKSPECIFIC	= (byte)0x03; // 3
 	public static final byte TON_SUBSCRIBERNUMBER	= (byte)0x04; // 4
	public static final byte TON_ALPHANUMERIC			= (byte)0x05; // 5
	public static final byte TON_ABBREVIATED			= (byte)0x06; // 6
	
	//Numeric Plain Indicator - NPI to be used in SME address parameter
  //Address_NPI
  public static final byte NPI_UNKNOWN        = (byte)0x00; // 0
  public static final byte NPI_ISDN           = (byte)0x01; // 1
  public static final byte NPI_X121           = (byte)0x03; // 2
  public static final byte NPI_TELEX          = (byte)0x04; // 3
  public static final byte NPI_LANDMOBILE    	= (byte)0x06; // 4
  public static final byte NPI_NATIONAL       = (byte)0x08; // 5
  public static final byte NPI_PRIVATE        = (byte)0x09; // 6
  public static final byte NPI_ERMES          = (byte)0x0A; // 7
  public static final byte NPI_INTERNET       = (byte)0x0E; // 8
  public static final byte NPI_WAPCLIENTID  	= (byte)0x12; // 9
	
	//Command Set
 	public static final int GENERIC_NACK           	= 0x80000000;
  public static final int BIND_RECEIVER           = 0x00000001;
  public static final int BIND_RECEIVER_RESP      = 0x80000001;
  public static final int BIND_TRANSMITTER        = 0x00000002;
  public static final int BIND_TRANSMITTER_RESP   = 0x80000002;
  public static final int QUERY_SM                = 0x00000003;
  public static final int QUERY_SM_RESP           = 0x80000003;
  public static final int SUBMIT_SM               = 0x00000004;
  public static final int SUBMIT_SM_RESP          = 0x80000004;
  public static final int DELIVER_SM              = 0x00000005;
  public static final int DELIVER_SM_RESP         = 0x80000005;
  public static final int UNBIND                  = 0x00000006;
  public static final int UNBIND_RESP             = 0x80000006;
  public static final int REPLACE_SM              = 0x00000007;
  public static final int REPLACE_SM_RESP         = 0x80000007;
  public static final int CANCEL_SM               = 0x00000008;
  public static final int CANCEL_SM_RESP          = 0x80000008;
  public static final int BIND_TRANSCEIVER        = 0x00000009;
  public static final int BIND_TRANSCEIVER_RESP   = 0x80000009;
  public static final int OUTBIND                 = 0x0000000B;
  public static final int ENQUIRE_LINK            = 0x00000015;
  public static final int ENQUIRE_LINK_RESP       = 0x80000015;
  public static final int SUBMIT_MULTI            = 0x00000021;
  public static final int SUBMIT_MULTI_RESP       = 0x80000021;
  public static final int ALERT_NOTIFICATION      = 0x00000102;
  public static final int DATA_SM                 = 0x00000103;
  public static final int DATA_SM_RESP            = 0x80000103;

	//Command Status
	public static final int ESME_ROK                = 0x00000000;
  public static final int ESME_RINVMSGLEN         = 0x00000001;
  public static final int ESME_RINVCMDLEN         = 0x00000002;
  public static final int ESME_RINVCMDID          = 0x00000003;
  public static final int ESME_RINVBNDSTS         = 0x00000004;
  public static final int ESME_RALYBND            = 0x00000005;
  public static final int ESME_RINVPRTFLG         = 0x00000006;
  public static final int ESME_RINVREGDLVFLG      = 0x00000007;
  public static final int ESME_RSYSERR            = 0x00000008;
  public static final int ESME_RINVSRCADR         = 0x0000000A;
  public static final int ESME_RINVDSTADR         = 0x0000000B;
  public static final int ESME_RINVMSGID          = 0x0000000C;
  public static final int ESME_RBINDFAIL          = 0x0000000D;
  public static final int ESME_RINVPASWD          = 0x0000000E;
  public static final int ESME_RINVSYSID          = 0x0000000F;
  public static final int ESME_RCANCELFAIL        = 0x00000011;
  public static final int ESME_RREPLACEFAIL       = 0x00000013;
  public static final int ESME_RMSGQFUL           = 0x00000014;
  public static final int ESME_RINVSERTYP         = 0x00000015;
  
  public static final int ESME_RADDCUSTFAIL       = 0x00000019;  // Failed to Add Customer
  public static final int ESME_RDELCUSTFAIL       = 0x0000001A;  // Failed to delete Customer
  public static final int ESME_RMODCUSTFAIL       = 0x0000001B;  // Failed to modify customer
  public static final int ESME_RENQCUSTFAIL       = 0x0000001C;  // Failed to Enquire Customer
  public static final int ESME_RINVCUSTID         = 0x0000001D;  // Invalid Customer ID
  public static final int ESME_RINVCUSTNAME       = 0x0000001F;  // Invalid Customer Name
  public static final int ESME_RINVCUSTADR        = 0x00000021;  // Invalid Customer Address
  public static final int ESME_RINVADR            = 0x00000022;  // Invalid Address
  public static final int ESME_RCUSTEXIST         = 0x00000023;  // Customer Exists
  public static final int ESME_RCUSTNOTEXIST      = 0x00000024;  // Customer does not exist
  public static final int ESME_RADDDLFAIL         = 0x00000026;  // Failed to Add DL
  public static final int ESME_RMODDLFAIL         = 0x00000027;  // Failed to modify DL
  public static final int ESME_RDELDLFAIL         = 0x00000028;  // Failed to Delete DL
  public static final int ESME_RVIEWDLFAIL        = 0x00000029;  // Failed to View DL
  public static final int ESME_RLISTDLSFAIL       = 0x00000030;  // Failed to list DLs
  public static final int ESME_RPARAMRETFAIL      = 0x00000031;  // Param Retrieve Failed
  public static final int ESME_RINVPARAM          = 0x00000032;  // Invalid Param
  
  public static final int ESME_RINVNUMDESTS       = 0x00000033;
  public static final int ESME_RINVDLNAME         = 0x00000034;
  
  public static final int ESME_RINVDLMEMBDESC     = 0x00000035;  // Invalid DL Member Description
  public static final int ESME_RINVDLMEMBTYP      = 0x00000038;  // Invalid DL Member Type
  public static final int ESME_RINVDLMODOPT       = 0x00000039;  // Invalid DL Modify Option
  
  public static final int ESME_RINVDESTFLAG       = 0x00000040;
  public static final int ESME_RINVSUBREP         = 0x00000042;
  public static final int ESME_RINVESMCLASS       = 0x00000043;
  public static final int ESME_RCNTSUBDL          = 0x00000044;
  public static final int ESME_RSUBMITFAIL        = 0x00000045;
  public static final int ESME_RINVSRCTON         = 0x00000048;
  public static final int ESME_RINVSRCNPI         = 0x00000049;
  public static final int ESME_RINVDSTTON         = 0x00000050;
  public static final int ESME_RINVDSTNPI         = 0x00000051;
  public static final int ESME_RINVSYSTYP         = 0x00000053;
  public static final int ESME_RINVREPFLAG        = 0x00000054;
  public static final int ESME_RINVNUMMSGS        = 0x00000055;
  public static final int ESME_RTHROTTLED         = 0x00000058;
  
  public static final int ESME_RPROVNOTALLWD      = 0x00000059;  // Provisioning Not Allowed
  
  public static final int ESME_RINVSCHED          = 0x00000061;
  public static final int ESME_RINVEXPIRY         = 0x00000062;
  public static final int ESME_RINVDFTMSGID       = 0x00000063;
  public static final int ESME_RX_T_APPN          = 0x00000064;
  public static final int ESME_RX_P_APPN          = 0x00000065;
  public static final int ESME_RX_R_APPN          = 0x00000066;
  public static final int ESME_RQUERYFAIL         = 0x00000067;
  
  public static final int ESME_RINVPGCUSTID       = 0x00000080;  // Paging Customer ID Invalid No such subscriber
  public static final int ESME_RINVPGCUSTIDLEN    = 0x00000081;  // Paging Customer ID length Invalid
  public static final int ESME_RINVCITYLEN        = 0x00000082;  // City Length Invalid
  public static final int ESME_RINVSTATELEN       = 0x00000083;  // State Length Invalid
  public static final int ESME_RINVZIPPREFIXLEN   = 0x00000084;  // Zip Prefix Length Invalid
  public static final int ESME_RINVZIPPOSTFIXLEN  = 0x00000085;  // Zip Postfix Length Invalid
  public static final int ESME_RINVMINLEN         = 0x00000086;  // MIN Length Invalid
  public static final int ESME_RINVMIN            = 0x00000087;  // MIN Invalid (i.e. No such MIN)
  public static final int ESME_RINVPINLEN         = 0x00000088;  // PIN Length Invalid
  public static final int ESME_RINVTERMCODELEN    = 0x00000089;  // Terminal Code Length Invalid
  public static final int ESME_RINVCHANNELLEN     = 0x0000008A;  // Channel Length Invalid
  public static final int ESME_RINVCOVREGIONLEN   = 0x0000008B;  // Coverage Region Length Invalid
  public static final int ESME_RINVCAPCODELEN     = 0x0000008C;  // Cap Code Length Invalid
  public static final int ESME_RINVMDTLEN         = 0x0000008D;  // Message delivery time Length Invalid
  public static final int ESME_RINVPRIORMSGLEN    = 0x0000008E;  // Priority Message Length Invalid
  public static final int ESME_RINVPERMSGLEN      = 0x0000008F;  // Periodic Messages Length Invalid
  public static final int ESME_RINVPGALERTLEN     = 0x00000090;  // Paging Alerts Length Invalid
  public static final int ESME_RINVSMUSERLEN      = 0x00000091;  // Short Message User Group Length Invalid
  public static final int ESME_RINVRTDBLEN        = 0x00000092;  // Real Time Data broadcasts Length Invalid
  public static final int ESME_RINVREGDELLEN      = 0x00000093;  // Registered Delivery Lenght Invalid
  public static final int ESME_RINVMSGDISTLEN     = 0x00000094;  // Message Distribution Lenght Invalid
  public static final int ESME_RINVPRIORMSG       = 0x00000095;  // Priority Message Length Invalid
  public static final int ESME_RINVMDT            = 0x00000096;  // Message delivery time Invalid
  public static final int ESME_RINVPERMSG         = 0x00000097;  // Periodic Messages Invalid
  public static final int ESME_RINVMSGDIST        = 0x00000098;  // Message Distribution Invalid
  public static final int ESME_RINVPGALERT        = 0x00000099;  // Paging Alerts Invalid
  public static final int ESME_RINVSMUSER         = 0x0000009A;  // Short Message User Group Invalid
  public static final int ESME_RINVRTDB           = 0x0000009B;  // Real Time Data broadcasts Invalid
  public static final int ESME_RINVREGDEL         = 0x0000009C;  // Registered Delivery Invalid
  public static final int ESME_RINVOPTPARSTREAM   = 0x0000009D;  // KIF IW Field out of data
  public static final int ESME_ROPTPARNOTALLWD    = 0x0000009E;  // Optional Parameter not allowed
  public static final int ESME_RINVOPTPARLEN      = 0x0000009F;  // Invalid Optional Parameter Length
  
  public static final int ESME_RMISSINGOPTPARAM   = 0x000000C3;
  public static final int ESME_RINVOPTPARAMVAL    = 0x000000C4;
  public static final int ESME_RDELIVERYFAILURE   = 0x000000FE;
  public static final int ESME_RUNKNOWNERR        = 0x000000FF;

  public static final int ESME_LAST_ERROR         = 0x0000012C;  // the value of the last error code

  public static final byte SZ_BYTE  = 1;
  public static final byte SZ_SHORT = 2;
  public static final byte SZ_INT   = 4;
  public static final byte SZ_LONG  = 8;
	
	public static final String CHAR_ENC         = "ASCII";
}

