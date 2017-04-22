package com.techstudio.app.controller;
import com.techstudio.app.entities.*;
import com.techstudio.app.*;

import com.techstudio.smpp.pdu.*;
import com.techstudio.smpp.*;
import com.techstudio.converter.*;
import java.util.*;
import com.techstudio.smpp.util.*;
import org.apache.log4j.*;

public class MTHandler implements SMPP_DefaultSetting, DefaultSetting{	
	Logger logger = null;

	ClientConnection conn;
	boolean stop 								= false;
	String validityperiod 			= "";
	byte summitsm_destaddrton		=0x1;
	byte summitsm_destaddrnpi		=0x1;
	byte summitsm_sourceaddrton	=0x0;
	byte summitsm_sourceaddrnpi	=0x0;
	byte summitsm_pid						=0x00;
	//DBHandler dbConn;
	String name = "";
	static short concatrefno = 10;
	String submitsm_servicetype = "";

	//public MTHandler(ClientConnection conn, DBHandler dbConn, String name, Logger logger){
	public MTHandler(ClientConnection conn, String name, Logger logger){
		this.name 	= name;
		this.logger 	= logger;		
		this.conn 	= conn;
		//this.dbConn = dbConn;
		summitsm_destaddrton = Byte.valueOf(ConnConfig.getProperty("summitsm_destaddrton")).byteValue();
		summitsm_destaddrnpi = Byte.valueOf(ConnConfig.getProperty("summitsm_destaddrnpi")).byteValue();

		summitsm_sourceaddrton = Byte.valueOf(ConnConfig.getProperty("summitsm_sourceaddrton")).byteValue();
		summitsm_sourceaddrnpi = Byte.valueOf(ConnConfig.getProperty("summitsm_sourceaddrnpi")).byteValue();

		submitsm_servicetype = ConnConfig.getProperty("submitsm_servicetype");

		if(submitsm_servicetype == null)
			submitsm_servicetype = DefaultSetting.SERVICETYPE_DEFAULT;

		logger.info("["+name+"]STARTED");
	}

	//for login
	public MTObj bindTransmitter(){
		String systemid = ConnConfig.getProperty("bindtransmitter_systemid");
		String password = ConnConfig.getProperty("bindtransmitter_password");
		String systemtype = ConnConfig.getProperty("bindtransmitter_systemtype");
		double intversion = Double.valueOf(ConnConfig.getProperty("bindtransmitter_interfaceversion")).doubleValue();
		byte interfaceversion;
		if (intversion==3.4)
			interfaceversion = SMPP_V34;
		else
			interfaceversion = SMPP_V33;

		//System.out.println("----"+intversion+"  "+interfaceversion+"  "+SMPP_V34);

		byte addrton = Byte.valueOf(ConnConfig.getProperty("bindtransmitter_addrton")).byteValue();
		byte addrnpi = Byte.valueOf(ConnConfig.getProperty("bindtransmitter_addrnpi")).byteValue();

		String addressrange= ConnConfig.getProperty("bindtransmitter_addressrange");
		logger.info("systemid         >> "+systemid);
		//logger.info("password         >> "+password);
		logger.info("systemtype       >> "+systemtype);
		logger.info("interfaceversion >> "+interfaceversion);
		logger.info("addrton          >> "+addrton);
		logger.info("addrnpi          >> "+addrnpi);
		logger.info("addressrange     >> "+addressrange);
		BindTransmitter bindtransmitter = new BindTransmitter(systemid, password , systemtype, 
				interfaceversion, addrton, addrnpi, addressrange);
		return new MTObj(bindtransmitter.getBytes(conn.getSequenceNo()), "BIND_TRANSMITTER");
	}

	public MTObj bindReceiver(){
		String systemid = ConnConfig.getProperty("bindreceiver_systemid");
		String password = ConnConfig.getProperty("bindreceiver_password");
		String systemtype = ConnConfig.getProperty("bindreceiver_systemtype");
		double intversion = Double.valueOf(ConnConfig.getProperty("bindreceiver_interfaceversion")).doubleValue();
		byte interfaceversion;
		if (intversion==3.4)
			interfaceversion = SMPP_V34;
		else 
			interfaceversion = SMPP_V33;

		byte addrton = Byte.valueOf(ConnConfig.getProperty("bindreceiver_addrton")).byteValue();
		byte addrnpi = Byte.valueOf(ConnConfig.getProperty("bindreceiver_addrnpi")).byteValue();

		String addressrange= ConnConfig.getProperty("bindreceiver_addressrange");

		BindReceiver bindreceiver = new BindReceiver(systemid, password , systemtype, 
				interfaceversion, addrton, addrnpi, addressrange);
		return new MTObj(bindreceiver.getBytes(conn.getSequenceNo()), "BIND_RECEIVER");
	}

	public MTObj[] submitSM (SendObj sendobj){
		MTObj[] mtobj = null;	
		try{
			String sourceaddr = sendobj.tpoa;
			logger.info("sourceaddr>>"+sourceaddr);
			int seqno = conn.getSequenceNo();
			logger.info("seqno>>"+seqno);
			logger.info("msgid>>"+sendobj.msgid);
			String message = sendobj.msg;
			logger.info("message>>"+message);
			conn.htMsgid.remove(""+seqno);						
			conn.htMsgid.put(""+seqno, ""+sendobj.msgid);

			sendobj.recp = sendobj.recp;
			/**Skip adding of plus sign
				if ( sendobj.recp.charAt(0)!='+'){
					if ( sendobj.recp.charAt(0)=='6' && sendobj.recp.charAt(1)=='5' ){

					}else{
						sendobj.recp="+"+sendobj.recp;
						/*
						if ( sendobj.recp.length()>3 ){
							String prefix = sendobj.recp.substring(0,3);
							String noneedappendrange = ConnConfig.getProperty("MSISDN_"+prefix);

							String prefix2 = sendobj.recp.substring(0,2);
							String noneedappendrange2 = ConnConfig.getProperty("MSISDN_"+prefix2);

							logger.info("checking for prefix(MSISDN_"+prefix+")>>"+noneedappendrange);
							logger.info("checking for prefix(MSISDN_"+prefix2+")>>"+noneedappendrange2);

							if ( (noneedappendrange!=null && noneedappendrange.equals("true")) || 
										(noneedappendrange2!=null && noneedappendrange2.equals("true")) ){
								//do nothing
								sendobj.recp="+"+sendobj.recp;
							}else{
								sendobj.recp="+00"+sendobj.recp;
							}
						}
			 *
					}
				}**/
			logger.info("destaddr>>"+sendobj.recp+"|type>>"+sendobj.msgtype+"|"+message);
			switch(sendobj.msgtype ){
			case 1://english sms


				byte[] byteascii = fromAsciitoGSM(message.getBytes());
				if(conn.isDialogue){
					byteascii = message.getBytes();
				}
				
				short lengthascii = (short)byteascii.length;
				byte encoding = DATAENCODING_ASCII;

				if ( message.length() >160 ){
					//encoding = DATAENCODING_BINARY;
					//message = new String(byteascii);
					//logger.info("message>>"+message);

					String[] msg = getSplit(message, 152);

					int totalmsgs = msg.length;
					mtobj = new MTObj[totalmsgs];
					String msgse = null;
					for ( int i=0; i<totalmsgs; i++ ){

						//logger.info("msg["+i+"]>>"+msg[i]);
						msgse = "050003710"+totalmsgs+"0"+(i+1)+""+HexConverter.toHexStr_notwork(msg[i]);
						//logger.info("msgse>>"+msgse);
//						try{
//							byteascii = msgse.getBytes("ASCII");
//						}catch(Exception ex){
//							byteascii = msgse.getBytes();
//						}
						byteascii = HexConverter.deBinaryCode(msgse);
						
						byte lengthhex = (byte)byteascii.length;

						SubmitSM summitsmascii = new SubmitSM(submitsm_servicetype, 
								summitsm_sourceaddrton, summitsm_sourceaddrnpi, sourceaddr,
								summitsm_destaddrton, summitsm_destaddrnpi, sendobj.recp, 
								ESMCLASS_BINARY, NULL_BYTE, NULL_BYTE, NULL_STRING, 
								//NULL_STRING, RECEIPTREQUIRED, FLAG_NOREPLACE , encoding,
								NULL_STRING, RECEIPTREQUIRED/* NULL_BYTE */, FLAG_NOREPLACE , encoding,
								NULL_BYTE, lengthhex, byteascii);
						seqno = conn.getSequenceNo();

						conn.htMsgid.remove(""+seqno);						
						conn.htMsgid.put(""+seqno, ""+sendobj.msgid);
						mtobj[i] = new MTObj(summitsmascii.getBytes(seqno), "SUMMIT_SM");
					}
					/*
					String hextstring = HexConverter.toHexStr_notwork(message);

					int totalmsgs = hextstring.length()/268;
					if ( hextstring.length()%268!=0 )
						totalmsgs++;

					mtobj = new MTObj[totalmsgs];
					for ( int i=0; i<totalmsgs; i++ ){

						seqno = conn.getSequenceNo();								
						int startindex = i*268;
						int endindex  = startindex+268;
						if ( endindex>hextstring.length() )
							endindex=hextstring.length();

						String msg = "050003710"+totalmsgs+"0"+(i+1)+""+hextstring.substring(startindex,endindex);
						byteascii = HexConverter.fromHexStrToBytes(msg);
						byte lengthhex = (byte)byteascii.length;
						SubmitSM summitsmascii = new SubmitSM(submitsm_servicetype, 
								summitsm_sourceaddrton, summitsm_sourceaddrnpi, sourceaddr,
								summitsm_destaddrton, summitsm_destaddrnpi, sendobj.recp, 
								ESMCLASS_BINARY, NULL_BYTE, NULL_BYTE, NULL_STRING, 
								NULL_STRING, RECEIPTREQUIRED, FLAG_NOREPLACE , encoding,
								NULL_BYTE, lengthhex, byteascii);
						seqno = conn.getSequenceNo();
						mtobj[i] = new MTObj(summitsmascii.getBytes(seqno), "SUMMIT_SM");											
					}
					 */
				}else{

					logger.info("recp  >> "+sendobj.recp);
					logger.info("message  >> "+message);							
					SubmitSM summitsmascii = new SubmitSM(submitsm_servicetype, 
							summitsm_sourceaddrton, summitsm_sourceaddrnpi, sourceaddr,
							summitsm_destaddrton, summitsm_destaddrnpi, sendobj.recp, 
							NULL_BYTE, summitsm_pid, NULL_BYTE, NULL_STRING, 
							//NULL_STRING, RECEIPTREQUIRED, FLAG_NOREPLACE , encoding,
							NULL_STRING, RECEIPTREQUIRED /*NULL_BYTE*/, FLAG_NOREPLACE , encoding,
							NULL_BYTE, lengthascii, byteascii);

					mtobj = new MTObj[1];
					mtobj[0] = new MTObj(summitsmascii.getBytes(seqno), "SUMMIT_SM");
				}

				break;	
			case 2://wappush - binary
				byte[] logobyte = HexConverter.fromHexStrToBytes(message);
				byte length = (byte)logobyte.length;
				SubmitSM summitsm = new SubmitSM(submitsm_servicetype, 
						summitsm_sourceaddrton, summitsm_sourceaddrnpi, sourceaddr,
						summitsm_destaddrton, summitsm_destaddrnpi, sendobj.recp, 
						ESMCLASS_BINARY, NULL_BYTE, NULL_BYTE, NULL_STRING, 
						//NULL_STRING, RECEIPTREQUIRED/*NULL_BYTE no receipt required*/, FLAG_NOREPLACE, DATAENCODING_BINARY,
						NULL_STRING, RECEIPTREQUIRED /*NULL_BYTE*/, FLAG_NOREPLACE, DATAENCODING_BINARY,
						NULL_BYTE, length, logobyte);
				mtobj = new MTObj[1];
				mtobj[0] = new MTObj(summitsm.getBytes(seqno), "SUMMIT_SM");	
				break;									
			case 5: 

				byte[] bytehexstr = HexConverter.fromHexStrToBytes(message);
				//String strUnicode = LangConverter.fromUnicodeStr(message, "MS936");
				String strUnicode = new String(bytehexstr, "MS936");

				byte[] bytearray = strUnicode.getBytes("UnicodeBigUnmarked");
				length = (byte)bytearray.length;
				logger.info("recp  >> "+sendobj.recp);
				logger.info("message  >> "+message);	
				SubmitSM summitsmchi = new SubmitSM(submitsm_servicetype, 
						summitsm_sourceaddrton, summitsm_sourceaddrnpi, sourceaddr,
						summitsm_destaddrton, summitsm_destaddrnpi, sendobj.recp, 
						NULL_BYTE, summitsm_pid, NULL_BYTE, NULL_STRING,
						//NULL_STRING, RECEIPTREQUIRED/*NULL_BYTE no receipt required*/, FLAG_NOREPLACE , DATAENCODING_UCS2,
						NULL_STRING, NULL_BYTE, FLAG_NOREPLACE , DATAENCODING_UCS2,
						NULL_BYTE, length, bytearray);
				mtobj = new MTObj[1];
				mtobj[0] = new MTObj(summitsmchi.getBytes(seqno), "SUMMIT_SM");
				break;
			case 4: 
				strUnicode = new String(message.getBytes(), "UTF-8");
				bytearray = strUnicode.getBytes("UnicodeBigUnmarked");
				length = (byte)bytearray.length;
				logger.info("recp  >> "+sendobj.recp);
				logger.info("message  >> "+message);	
				SubmitSM summitsmutf = new SubmitSM(submitsm_servicetype, 
						summitsm_sourceaddrton, summitsm_sourceaddrnpi, sourceaddr,
						summitsm_destaddrton, summitsm_destaddrnpi, sendobj.recp, 
						NULL_BYTE, summitsm_pid, NULL_BYTE, NULL_STRING, 
						//NULL_STRING, RECEIPTREQUIRED/*NULL_BYTE no receipt required*/, FLAG_NOREPLACE , DATAENCODING_UCS2,
						NULL_STRING, NULL_BYTE, FLAG_NOREPLACE , DATAENCODING_UCS2,
						NULL_BYTE, length, bytearray);
				mtobj = new MTObj[1];
				mtobj[0] = new MTObj(summitsmutf.getBytes(seqno), "SUMMIT_SM");
				break;
			case 3: 

				message = HexConverter.toHexStr(message);
				logger.info("message  >> "+message);	
				if ( message.length()<=280 ){

					bytearray = HexConverter.deBinaryCode(message);//.fromHexStrToBytes( message);
					//bytearray = HexConverter.fromHexStrToBytes(message);
					length = (byte)bytearray.length;
					logger.info("recp  >> "+sendobj.recp);
					logger.info("message  >> "+message);	

					SubmitSM summitsmucs2 = new SubmitSM(submitsm_servicetype, 
							summitsm_sourceaddrton, summitsm_sourceaddrnpi, sourceaddr,
							summitsm_destaddrton, summitsm_destaddrnpi, sendobj.recp, 
							NULL_BYTE, summitsm_pid, NULL_BYTE, NULL_STRING, 
							//NULL_STRING, RECEIPTREQUIRED/*NULL_BYTE no receipt required*/, FLAG_NOREPLACE , DATAENCODING_UCS2,
							NULL_STRING, NULL_BYTE, FLAG_NOREPLACE , DATAENCODING_UCS2,
							NULL_BYTE, length, bytearray);
					mtobj = new MTObj[1];
					mtobj[0] = new MTObj(summitsmucs2.getBytes(seqno), "SUMMIT_SM");
				}else{
					encoding = DATAENCODING_UCS2;
					String hextstring = message; //HexConverter.toHexStr(message);

					int totalmsgs = hextstring.length()/268;
					if ( hextstring.length()%268!=0 )
						totalmsgs++;

					mtobj = new MTObj[totalmsgs];
					for ( int i=0; i<totalmsgs; i++ ){

						seqno = conn.getSequenceNo();								
						int startindex = i*268;
						int endindex  = startindex+268;
						if ( endindex>hextstring.length() )
							endindex=hextstring.length();

						String msg = "050003710"+totalmsgs+"0"+(i+1)+""+hextstring.substring(startindex,endindex);
						byteascii = HexConverter.deBinaryCode(msg);
						byte lengthhex = (byte)byteascii.length;
						SubmitSM summitsmascii = new SubmitSM(submitsm_servicetype, 
								summitsm_sourceaddrton, summitsm_sourceaddrnpi, sourceaddr,
								summitsm_destaddrton, summitsm_destaddrnpi, sendobj.recp, 
								ESMCLASS_BINARY, NULL_BYTE, NULL_BYTE, NULL_STRING, 
								//NULL_STRING, RECEIPTREQUIRED/*NULL_BYTE no receipt required*/, FLAG_NOREPLACE , encoding,
								NULL_STRING, NULL_BYTE, FLAG_NOREPLACE , encoding,
								NULL_BYTE, lengthhex, byteascii);
						seqno = conn.getSequenceNo();

						conn.htMsgid.remove(""+seqno);						
						conn.htMsgid.put(""+seqno, ""+sendobj.msgid);
						mtobj[i] = new MTObj(summitsmascii.getBytes(seqno), "SUMMIT_SM");											
					}						
				}
				break;									
			}					
		}catch(Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(5000);
			}catch(Exception e2){}
		}
		return mtobj;
	}	
	private String[] getSplit(String s, int count) {
		int leng = (s.length() - 1) / count + 1;
		String[] splitString = new String[leng];
		for (int i = 0; i < leng; i++) {
			if (i == leng - 1) {
				splitString[i] = s.substring(i * count, s.length());
			} else {
				splitString[i] = s.substring(i * count, i * count + count);
			}
		}
		return splitString;
	}
	private byte[] fromAsciitoGSM(byte[] byteascii){
		byte[] newbyteascii = null;
		byte lengthascii = (byte)byteascii.length;
		for ( int a=0; a<lengthascii; a++ ){
			switch( byteascii[a]){
			case 64://@
				logger.info("@:"+64+"| to "+0);
				byteascii[a]=0;
				break;
			case 36://$
				logger.info("$:"+36+"| to "+2);
				byteascii[a]=2;
				break;
			case 95://_
				byteascii[a]=17;
				break;
			case (byte)161://inverted !
				byteascii[a]=64;
				break;
			case 123://{
				logger.info("test:"+123);
				newbyteascii = new byte[lengthascii+1];
				for ( byte z=0; z<a; z++ )
					newbyteascii[z]=byteascii[z];
				newbyteascii[a]=27;
				newbyteascii[a+1]=40;
				for ( int z=a+1; z<lengthascii; z++ )
					newbyteascii[z+1]=byteascii[z];
				byteascii=newbyteascii;
				lengthascii = (byte)byteascii.length;
				break;
			case 125://{
				newbyteascii = new byte[lengthascii+1];
				for ( byte z=0; z<a; z++ )
					newbyteascii[z]=byteascii[z];
				newbyteascii[a]=27;
				newbyteascii[a+1]=41;
				byteascii[a]=0;	
				for ( int z=a+1; z<lengthascii; z++ )
					newbyteascii[z+1]=byteascii[z];
				byteascii=newbyteascii;
				lengthascii = (byte)byteascii.length;
				break;		
			case 91://{
				newbyteascii = new byte[lengthascii+1];
				for ( byte z=0; z<a; z++ )
					newbyteascii[z]=byteascii[z];
				newbyteascii[a]=27;
				newbyteascii[a+1]=60;
				byteascii[a]=0;	
				for ( int z=a+1; z<lengthascii; z++ )
					newbyteascii[z+1]=byteascii[z];
				byteascii=newbyteascii;
				lengthascii = (byte)byteascii.length;
				break;
			case 93://{
				newbyteascii = new byte[lengthascii+1];
				for ( byte z=0; z<a; z++ )
					newbyteascii[z]=byteascii[z];
				newbyteascii[a]=27;
				newbyteascii[a+1]=62;
				byteascii[a]=0;	
				for ( int z=a+1; z<lengthascii; z++ )
					newbyteascii[z+1]=byteascii[z];
				byteascii=newbyteascii;
				lengthascii = (byte)byteascii.length;
				break;	
			}
		}//end for	
		return byteascii;
	}
	public MTObj enquireLink(){
		EnquireLink enquirelink = new EnquireLink();
		return (new MTObj(enquirelink.getBytes(conn.getSequenceNo()),"ENQUIRE_LINK"));
	}

	public MTObj querySM(long msgid, String messageid, String tpoa){	
		tpoa="";
		QuerySM querysm = new QuerySM(messageid, summitsm_sourceaddrton, summitsm_sourceaddrnpi, tpoa);
		int seqno=conn.getSequenceNo();
		//dbConn.updateSequenceNo(msgid, ""+seqno);
		return (new MTObj(querysm.getBytes(seqno),"QUERY_SM"));
	}	

	public MTObj unbind(){
		Unbind unbind = new Unbind ();
		return (new MTObj(unbind.getBytes(conn.getSequenceNo()), "UNBIND"));
	}

	public boolean stopServer(){
		return stop;
	}
}

