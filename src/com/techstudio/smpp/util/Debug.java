package com.techstudio.smpp.util;
import org.apache.log4j.*;

public class Debug implements com.techstudio.smpp.DefaultSetting
{

	public static void printCommandStatus(int status, Logger logger){

		switch(status){
			case ESME_ROK:
				logger.info("DEBUG : ESME_ROK : no error");
				break;
			case ESME_RINVMSGLEN:
				logger.info("DEBUG : ESME_RINVMSGLEN : Message length is invalid");
				break;
			case ESME_RINVCMDLEN:
				logger.info("DEBUG : ESME_RINVCMDLEN : command length is invalid");
				break;
			case ESME_RINVCMDID:
				logger.info("DEBUG : ESME_RINVCMDID : invalid command length");
				break;
			case ESME_RINVBNDSTS:
				logger.info("DEBUG : ESME_RINVBNDSTS : incorrect bind status for given command");
				break;
			case ESME_RALYBND:
				logger.info("DEBUG : ESME_RALYBND : esme already in bound state");
				break;
			case ESME_RINVPRTFLG:
				logger.info("DEBUG : ESME_RINVPRTFLG : invalid priority flag");
				break;
			case ESME_RINVREGDLVFLG:
				logger.info("DEBUG : ESME_RINVREGDLVFLG : invalid registered delivery flag");
				break;
			case ESME_RSYSERR:
				logger.info("DEBUG : ESME_RSYSERR : system error");
				break;
			case ESME_RINVSRCADR:
				logger.info("DEBUG : ESME_RINVSRCADR : invalid source address");
				break;
			case ESME_RINVDSTADR:
				logger.info("DEBUG : ESME_RINVDSTADR : invalid dest addr");
				break;
			case ESME_RINVMSGID:
				logger.info("DEBUG : ESME_RINVMSGID : message id is invalid");
				break;
			case ESME_RBINDFAIL:
				logger.info("DEBUG : ESME_RBINDFAIL : bind failed");
				break;
			case ESME_RINVPASWD:
				logger.info("DEBUG : ESME_RINVPASWD : invalid password");
				break;
			case ESME_RINVSYSID:
				logger.info("DEBUG : ESME_RINVSYSID : invalid system id");
				break;
			case ESME_RCANCELFAIL:
				logger.info("DEBUG : ESME_RCANCELFAIL : cancel sm fail");
				break;
			case ESME_RREPLACEFAIL:
				logger.info("DEBUG : ESME_RREPLACEFAIL : replace sm fail");
				break;
			case ESME_RMSGQFUL:
				logger.info("DEBUG : ESME_RMSGQFUL : message queue full");
				break;
			case ESME_RINVSERTYP:
				logger.info("DEBUG : ESME_RINVSERTYP : invalid service type");
				break;
			case ESME_RDELCUSTFAIL:
				logger.info("DEBUG : ESME_RDELCUSTFAIL : Failed to delete Customer");
				break;
			case ESME_RMODCUSTFAIL:
				logger.info("DEBUG : ESME_RMODCUSTFAIL : Failed to modify customer");
				break;
			case ESME_RENQCUSTFAIL:
				logger.info("DEBUG : ESME_RENQCUSTFAIL : Failed to Enquire Customer");
				break;
			case ESME_RINVCUSTID:
				logger.info("DEBUG : ESME_RINVCUSTID : Invalid Customer ID");
				break;
			case ESME_RINVCUSTNAME:
				logger.info("DEBUG : ESME_RINVCUSTNAME : Invalid Customer Name");
				break;
			case ESME_RINVCUSTADR:
				logger.info("DEBUG : ESME_RADDCUSTFAIL : Invalid Customer Address");
				break;
			case ESME_RINVADR:
				logger.info("DEBUG : ESME_RINVCUSTNAME : Invalid Address");
				break;
			case ESME_RCUSTEXIST:
				logger.info("DEBUG : ESME_RADDCUSTFAIL : Customer Exists");
				break;
			case ESME_RCUSTNOTEXIST:
				logger.info("DEBUG : ESME_RINVCUSTNAME : Customer does not exist");
				break;
			case ESME_RADDDLFAIL:
				logger.info("DEBUG : ESME_RADDCUSTFAIL : Failed to Add DL");
				break;
			case ESME_RMODDLFAIL:
				logger.info("DEBUG : ESME_RINVCUSTNAME : Failed to modify DL");
				break;
			case ESME_RDELDLFAIL:
				logger.info("DEBUG : ESME_RADDCUSTFAIL : Failed to Delete DL");
				break;
			case ESME_RVIEWDLFAIL:
				logger.info("DEBUG : ESME_RINVCUSTNAME : Failed to View DL");
				break;
			case ESME_RLISTDLSFAIL:
				logger.info("DEBUG : ESME_RADDCUSTFAIL : Failed to list DLs");
				break;
			case ESME_RPARAMRETFAIL:
				logger.info("DEBUG : ESME_RINVCUSTNAME : Param Retrieve Failed");
				break;
			case ESME_RINVPARAM:
				logger.info("DEBUG : ESME_RADDCUSTFAIL : Invalid Param");
				break;
			case ESME_RINVPGCUSTIDLEN:
				logger.info("DEBUG : ESME_RINVPGCUSTIDLEN : Paging Customer ID length Invalid");
				break;
			case ESME_RINVDSTNPI:
				logger.info("DEBUG : ESME_RINVDSTNPI : Invalid destination addr NPI");
				break;
			default:
				logger.info("DEBUG : UNKNOWN ERROR : "+status);
		}
	}
	
	public static void printBytes(byte[] data, String who){
		Logger logger = ConnLogger.getLogger();

		for ( int i=0; i<data.length; i++ ){
			logger.info(who+"["+i+"] = 0x"+data[i]);
		}
	}
}
/*
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
*/
