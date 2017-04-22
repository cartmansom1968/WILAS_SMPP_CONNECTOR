package com.techstudio.app.controller;
import com.techstudio.app.*;
import com.techstudio.app.entities.*;
import com.techstudio.reportlog.DRLogger;
import com.techstudio.smpp.*;
import com.techstudio.smpp.util.*;
import com.techstudio.smpp.pdu.*;

import java.util.Date;
import java.util.Hashtable;

import com.techstudio.wilasbroadcast.messaging.sms.SMSMessagingObj;
import org.apache.log4j.*;

public class MOHandler implements SMPP_DefaultSetting, DefaultSetting{	
	Logger logger = null;

	String systemid;
	int bindstatus = BINDSTATUS_BINDED;

	ClientConnection conn;
	//DBHandler dbconn;
	boolean stop = false;
	Date poolingreceived = new Date();
	String name = "";


	//public MOHandler(ClientConnection conn,  DBHandler dbconn, String name, Logger logger){
	public MOHandler(ClientConnection conn, String name, Logger logger){
		this.name 	= name;
		this.conn 	= conn;
		//this.dbconn = dbconn;
		this.logger = logger;

		logger.info("["+name+"]STARTED");
	}

	private String packTelcoMsgID(String messageid){
		if(messageid.indexOf("-") >=0 )
			messageid = messageid.replaceAll("-", "");
		if(messageid.indexOf("+") >=0 )
			messageid = messageid.substring(0, messageid.indexOf("+"));
		if(messageid.indexOf("!") >=0 )
			messageid = messageid.substring(messageid.indexOf("!")+1);

		return messageid;
	}

	public synchronized MTObj unpack(MOObj object){
		
		
		byte[] objbyte	= object.getBytes();

		ByteBuffer bytebuffer = new ByteBuffer(objbyte);
		int commandid = bytebuffer.removeInt();		
		byte[] byteleft	= bytebuffer.getBytes();

		//System.out.println("Commandid "+commandid);
		switch(commandid){
		case BIND_RECEIVER_RESP:
			logger.info("MOHandler >> BIND_RECEIVER_RESP");
			BindReceiverResp bindreceiverresp = new BindReceiverResp(byteleft);
			int recvcomdstatus = bindreceiverresp.getCommandStatus();
			if (recvcomdstatus==ESME_ROK)
				bindstatus = BINDSTATUS_BINDRECEIVER;
			else
				bindstatus = BINDSTATUS_INIT;
			Debug.printCommandStatus(recvcomdstatus, logger);
			systemid = bindreceiverresp.getSystemID();
			poolingreceived = new Date();
			break;
		case BIND_TRANSMITTER_RESP:
			logger.info("MOHandler >> BIND_TRANSMITTER_RESP");
			BindTransmitterResp bindtransmitterresp = new BindTransmitterResp(byteleft);
			int transcmdstatus = bindtransmitterresp.getCommandStatus();
			if (transcmdstatus==ESME_ROK)
				bindstatus = BINDSTATUS_BINDTRANSMITTER;
			else
				bindstatus = BINDSTATUS_INIT;
			Debug.printCommandStatus(transcmdstatus, logger);
			systemid = bindtransmitterresp.getSystemID();
			poolingreceived = new Date();
			break;

		case DELIVER_SM:
			logger.info("MOHandler >> DELIVER_SM");

			DeliverSM deliversm = new DeliverSM(byteleft);
			byte encoding = deliversm.getDataCoding();
			byte[] bytemessage = deliversm.getMessage();
			String sender = deliversm.getSourceAddr();
			String dest = deliversm.getDestAddr();
			logger.info("DR|sender >> "+sender+"|msg >> "+ new String(deliversm.getMessage()));

			if ( deliversm.getESMClass()==ESMCLASS_DR ){
				String msg = new String(deliversm.getMessage());

				DeliveryReceipt dr = new DeliveryReceipt(msg);
				String state = dr.getState();
				String messageid = dr.getMessageID();
                String err = dr.getError();
				logger.info("DR|org messageid >> "+ messageid);

				String hexstrid = packTelcoMsgID(messageid);

				//	Long.toHexString(Long.parseLong(messageid));//Integer.toHexString(Integer.parseInt(messageid));
				for ( int i=hexstrid.length(); i<8; i++ )
					hexstrid="0"+hexstrid;
				logger.info("hexstr messageid >> "+ hexstrid);
				hexstrid=hexstrid.toUpperCase();
				logger.info("state >> "+ state);

                //System.out.println("msgId "+messageid+"  len "+messageid.length());
				if (state.equals("UNDELIV")) {
					DRLogger.getInstance().recvdStatus(messageid,"UNDELIV", err);
				} else {
					DRLogger.getInstance().recvdStatus(messageid,"DELIVERED", "");
				}
				/*--------------SOM TO REVIEW LATER
				if (state.equals("DELIVRD")){ 
					DRObj obj = new DRObj();
					obj.telcomsgid=hexstrid;
					obj.status=4;
					obj.remark="DELIVERED";
					dbconn.addDR( obj);
					//boolean status = dbconn.updateDRStatus(hexstrid, 4, "DELIVERED");
					//logger.info("status:"+status);
				} else if(state.equals("UNDELIV")){

					DRObj obj = new DRObj();
					obj.telcomsgid=hexstrid;
					obj.status=5;
					obj.remark="UNDELIV";
					dbconn.addDR( obj);
					//boolean status = dbconn.updateDRStatus(hexstrid, 5, "UNDELIV");
					try{
						String[] campaignID = dbconn.getCampaignIDByMTTelcoMSGID(hexstrid);

						if(campaignID == null){
							campaignID = dbconn.getCampaignIDByMTMSISDN(sender);
						}
						if(campaignID != null)
						{
							dbconn.updateDRProcess(campaignID[1]);
							String shortcode = dbconn.getTPOAByCampaignID(campaignID[0]);

							logger.info("INSERT UNDELIV RECORD TO MO QUEUE|campaignID["+campaignID[0]+"]|shortcode["+shortcode+"]");
							
							if(campaignID != null && shortcode != null){
								MessageObj messageObj = new MessageObj(deliversm.getSequenceNo() , shortcode , sender, 
										com.techstudio.tmms.Const.CONTENT_TYPE_TEXT_SMS, "ISPREERR");

								messageObj.setCampaignID(Long.parseLong(campaignID[0]));
								messageObj.setFlag(com.techstudio.tmms.Const.CAMPAIGNFLAG_UDINTERACTIVE);
								try{
									Hashtable<String, String> extra = new Hashtable<String, String>();
									extra.put("IPXTIME", DateHelper.getTimeNow("yyyy-MM-dd HH:mm:ss:SSS"));
									extra.put("SMS_SENDER",sender);
									extra.put("SMS_BODY", "ISPREERR");
									extra.put("OPERATOR", "STARHUB");
									extra.put("IPXMSGID", deliversm.getSequenceNo()+"");

									messageObj.setMessageTable(extra);
								}catch(Exception ex){}


								try{
									if (conn.qConn.isStarted()){
										boolean result= conn.qConn.sendMessage(messageObj, conn.moqueueName);

										logger.info("MO|TO QUEUE|" + messageObj.toString() + "|SENT: [" + result + "]");
									}else{
										logger.info(conn.moqueueName + " RECEIVER: Trying to restart OpenJMS connection, if exception is raised, restart OpenJMS");
										conn.qConn = new QueueConnector(conn.queueUrl);
										logger.info(conn.moqueueName + " RECEIVER: OpenJMS connection restarted");
									}
								}catch(Exception e){
									logger.error("Exception Insert to Queue", e);
								}
							}
						}else{
							logger.info("UNKNOWN MSGID");
						}
					}catch(Exception ex){
						logger.error("Exception Insert to Queue2", ex);
					}

				}
				else{
					DRObj obj = new DRObj();
					obj.telcomsgid=hexstrid;
					obj.status=5;
					obj.remark=state;
					dbconn.addDR( obj);					
					//boolean status = dbconn.updateDRStatus(hexstrid, 5, state);					
					//logger.info("status:"+status);
				}
				*/
			}else{
				//handle mo message
				//if ( conn.isUniqueSequenceNo(String.valueOf(deliversm.getSequenceNo()))==true )
				if ( encoding==DATAENCODING_ASCII )
					bytemessage=fromGSMtoAscii(bytemessage);

				ReceiveObj recvobj = new ReceiveObj(sender, bytemessage, encoding);
				if ( dest!=null && dest.charAt(0)=='+' ){
					dest=dest.substring(1,dest.length());
				}


				if(conn.truncateShortcode > 0 && dest !=null && dest.length() > conn.truncateShortcode){
					recvobj.setShortCode(Long.parseLong(dest.substring(0, dest.length()-conn.truncateShortcode)));
				}else
				{
					recvobj.setShortCode(Long.parseLong(dest));
				}

				logger.info("MSG ENC >> "+ encoding+"|MSG >> ["+ recvobj.getMessage()+"]");

				if(recvobj.getMessage() != null && recvobj.getMessage().trim().length() < 1)
				{
					recvobj.setMessage(new String(deliversm.getMessage()));
				}

				//try{
					//logger.info("INSERT RECORD TO MO QUEUE");

					//SMSMessagingObj msg = new SMSMessagingObj(recvobj.getSender(),recvobj.getShortCode()+"",recvobj.getMessage(),SMSMessagingObj.TEXT,null);


					//MessageObj messageObj = new MessageObj(deliversm.getSequenceNo() , recvobj.getShortCode()+"" , recvobj.getSender(),
					//		com.techstudio.tmms.Const.CONTENT_TYPE_TEXT_SMS, recvobj.getMessage());

					//messageObj.setFlag(com.techstudio.tmms.Const.CAMPAIGNFLAG_UDINTERACTIVE);

					//try{
					//	Hashtable<String, String> extra = new Hashtable<String, String>();
					//	extra.put("IPXTIME", DateHelper.getTimeNow("yyyy-MM-dd HH:mm:ss:SSS"));
					//	extra.put("SMS_SENDER",recvobj.getSender());
					//	extra.put("SMS_BODY", recvobj.getMessage());
					//	extra.put("OPERATOR", "STARHUB");
					//	extra.put("IPXMSGID", deliversm.getSequenceNo()+"");

					//	messageObj.setMessageTable(extra);
					//}catch(Exception ex){}

					//messageObj = dbconn.getCurrentCampagin(recvobj.getShortCode()+"", messageObj);
					//dbconn.writeMoTraffic(messageObj);

					try{
						if (conn.mobox!=null) {
							logger.info("INSERT RECORD TO MO QUEUE");
							int type = 1; //TEXT

							SMSMessagingObj msg = new SMSMessagingObj(recvobj.getSender(),recvobj.getShortCode()+"",recvobj.getMessage(),1,null);
							conn.mobox.send(msg.getBody(),msg);
							logger.info("MO|TO QUEUE|" + msg.toString() + "|SENT: [" + true + "]");
						}

						//if (conn.qConn.isStarted()){
						//	boolean result= conn.qConn.sendMessage(messageObj, conn.moqueueName);

						//	logger.info("MO|TO QUEUE|" + messageObj.toString() + "|SENT: [" + result + "]");
						//}else{
						//	logger.info(conn.moqueueName + " RECEIVER: Trying to restart OpenJMS connection, if exception is raised, restart OpenJMS");
						//	conn.qConn = new QueueConnector(conn.queueUrl);
						//	logger.info(conn.moqueueName + " RECEIVER: OpenJMS connection restarted");
						//}


					}catch(Exception e){
						logger.error("Exception Insert to Queue", e);
					}

				//}catch(Exception ex){
				//	logger.error("Exception Insert to Queue2", ex);}
				//dbconn.insertMsg(recvobj);
			}
			DeliverSMResp resp = new DeliverSMResp(deliversm.getSequenceNo());
			MTObj mtobj = new MTObj(resp.getBytes(conn.getSequenceNo()), "DELIVER_SM_RESP");
			poolingreceived = new Date();
			return mtobj;

		case OUTBIND:
			logger.info("MOHandler >> OUTBIND");
			OutBind outbind = new OutBind(byteleft);
			break;

		case SUBMIT_SM_RESP:
			logger.info("MOHandler >> SUBMIT_SM_RESP");
			SubmitSMResp submitresp = new SubmitSMResp(byteleft);
			//logger.info("MOHandler=");
			int submitsmstatus = submitresp.getCommandStatus();
			logger.info("MOHandler="+submitsmstatus);
			Object obj = conn.htMsgid.get(""+submitresp.getSequenceNo());
			if ( obj==null ){
				try {
					logger.info("seqno >> "+submitresp.getSequenceNo()+"|message id >> "+submitresp.getMessageID());
				} catch (Exception e) {
				}
				logger.info("Unknown message id");
				break;
			}

			String msgid = (String)(obj);	
			conn.htMsgid.remove(""+submitresp.getSequenceNo());										
			if (submitsmstatus==ESME_ROK){ 
				logger.info("message id >> "+submitresp.getMessageID());
				logger.info("msgid >> "+msgid);
				String externalMsgId = packTelcoMsgID(submitresp.getMessageID());
				//String[] parts = externalMsgId.split("!");
				//if (parts.length>1) {
				//	externalMsgId = parts[0];
				//}
				//System.out.println("xxxxxxxxx  "+msgid+" <"+externalMsgId+">  len "+externalMsgId.length());
				//System.out.println("**** "+externalMsgId.indexOf(0));
				if (externalMsgId.indexOf(0)>=0) {
					externalMsgId = externalMsgId.substring(0, externalMsgId.indexOf(0));
				}
				if (msgid!=null && externalMsgId!=null) {
					DRLogger.getInstance().mapExternalId2InternalId(externalMsgId, msgid);
				};
				conn.processResponse(submitresp.getSequenceNo(),true);
				//dbconn.updateMsg(Long.parseLong(msgid), packTelcoMsgID(submitresp.getMessageID()), 2);
			}else{
				conn.processResponse(submitresp.getSequenceNo(),false);
				//dbconn.updateMsg(Long.parseLong(msgid), "", 3);
			}

			Debug.printCommandStatus(submitsmstatus, logger);
			poolingreceived = new Date();
			break;

		case ENQUIRE_LINK_RESP:
			/*
			logger.info("MOHandler >> ENQUIRE_LINK_RESP");
			*/
			poolingreceived = new Date();
			break;

		case ENQUIRE_LINK:
			logger.info("MOHandler >> ENQUIRE_LINK");
			EnquireLink enlink = new EnquireLink(byteleft);
			int seqno = enlink.getSequenceNo();
			EnquireLinkResp res = new EnquireLinkResp();
			MTObj retobj = new MTObj(res.getBytes(seqno), "ENQUIRE_LINK_RESP");
			poolingreceived = new Date();
			return retobj;

		case UNBIND_RESP:
			logger.info("MOHandler >> UNBIND_RESP");
			stop = true;
			break;

		case QUERY_SM_RESP:
			logger.info("MOHandler >> QUERY_SM_RESP");
			QuerySMResp querysmresp = new QuerySMResp(byteleft);
			int querysmstatus = querysmresp.getCommandStatus();
			logger.info("MOHandler >> QUERY_SM_RESP >> querysmstatus: "+querysmstatus);
			String messageid = querysmresp.getMessageID();
			messageid=messageid.trim();
			logger.info("MOHandler >> QUERY_SM_RESP >> messageid: "+messageid);
			String finaldate = querysmresp.getFinalDate();
			logger.info("MOHandler >> QUERY_SM_RESP >> finaldate: "+finaldate);
			int messagestate = querysmresp.getMessageState();
			logger.info("MOHandler >> QUERY_SM_RESP >> messagestate: "+messagestate);
			int errorcode = querysmresp.getErrorCode();
			logger.info("MOHandler >> QUERY_SM_RESP >> errorcode: "+errorcode);
			if (querysmstatus==ESME_ROK){ 
				logger.info("message id >> "+querysmresp.getMessageID());
				/*
				dbconn.updateMsgFlagBySeqNo(String.valueOf(submitresp.getSequenceNo()), FLAG_OK,submitresp.getMessageID());
				*/

				//dbconn.updateDRStatus(messageid, 4, "delivered");

			}else{
				//dbconn.updateDRStatus(messageid, 5, "status="+querysmstatus+",messagestate="+messagestate);

			}

			Debug.printCommandStatus(querysmstatus, logger);
			poolingreceived = new Date();
			break;

		case REPLACE_SM_RESP:
		case CANCEL_SM_RESP:
		case ALERT_NOTIFICATION:
		case SUBMIT_MULTI_RESP:
			logger.info("MOHandler >> REPLACE_SM_RESP|CANCEL_SM_RESP|ALERT_NOTIFICATION|SUBMIT_MULTI_RESP");
			break;
		default:

			logger.info("MOHandler >> UNKNOWN");
			break;
		}		
		return null;
	}

	private byte[] fromGSMtoAscii(byte[] byteascii){
		byte[] newbyteascii = null;
		byte lengthascii = (byte)byteascii.length;
		for ( int a=0; a<lengthascii; a++ ){
			switch( byteascii[a]){
			case 0://@
				byteascii[a]=64;
				break;
			case 2:
				byteascii[a]=36;
				break;
			case 17://_
				byteascii[a]=95;
				break;
			case 27://{
				if ( a+1<lengthascii ){
					switch(byteascii[a+1]){
					case 40:
						newbyteascii = new byte[lengthascii-1];
						for ( byte z=0; z<a; z++ )
							newbyteascii[z]=byteascii[z];
						newbyteascii[a]=123;
						for ( int z=a+2; z<lengthascii; z++ )
							newbyteascii[z-1]=byteascii[z];
						byteascii=newbyteascii;
						lengthascii = (byte)byteascii.length;
						break;
					case 41:
						newbyteascii = new byte[lengthascii-1];
						for ( byte z=0; z<a; z++ )
							newbyteascii[z]=byteascii[z];
						newbyteascii[a]=125;
						for ( int z=a+2; z<lengthascii; z++ )
							newbyteascii[z-1]=byteascii[z];
						byteascii=newbyteascii;
						lengthascii = (byte)byteascii.length;
						break;
					case 60:
						newbyteascii = new byte[lengthascii-1];
						for ( byte z=0; z<a; z++ )
							newbyteascii[z]=byteascii[z];
						newbyteascii[a]=91;
						for ( int z=a+2; z<lengthascii; z++ )
							newbyteascii[z-1]=byteascii[z];
						byteascii=newbyteascii;
						lengthascii = (byte)byteascii.length;
						break;							
					case 62:
						newbyteascii = new byte[lengthascii-1];
						for ( byte z=0; z<a; z++ )
							newbyteascii[z]=byteascii[z];
						newbyteascii[a]=93;
						for ( int z=a+2; z<lengthascii; z++ )
							newbyteascii[z-1]=byteascii[z];
						byteascii=newbyteascii;
						lengthascii = (byte)byteascii.length;
						break;									
					}
				}
				break;
			default:

			}
		}//end for	
		return byteascii;
	}

	public boolean stopServer(){
		return stop;
	}

	public int getBindStatus(){
		return bindstatus;
	}

	public Date getPoolingDateReceived(){
		return poolingreceived;
	}
	

	public MTObj enquireLink(){
		EnquireLink enquirelink = new EnquireLink();
		return (new MTObj(enquirelink.getBytes(conn.getSequenceNo()),"ENQUIRE_LINK"));
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
}

