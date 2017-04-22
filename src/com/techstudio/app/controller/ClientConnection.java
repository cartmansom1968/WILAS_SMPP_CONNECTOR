package com.techstudio.app.controller;
import com.techstudio.app.*;
import com.techstudio.app.entities.*;
import com.techstudio.reportlog.DRLogger;
import com.techstudio.reportlog.DataLogger;
import com.techstudio.smpp.*;
import com.ts.wilasmarketing.messaging.sms.SMSIntRespSenderBoxImpl;
import com.ts.wilasmarketing.messaging.sms.SMSMessagingObj;
import com.ts.wilasmarketing.messaging.sms.SMSReceiverBoxImpl;
import com.ts.wilasmarketing.base.*;

import org.apache.log4j.*;

import com.techstudio.smpp.util.*;

import com.ts.wilasmarketing.messaging.*;

import java.nio.charset.*;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;


import java.util.*;

public class ClientConnection extends Connection implements SMPP_DefaultSetting{	


	public boolean isDialogue = true;

	int sequencenumber = 1;

    String accesscode = "";
	Start mainclass;

	MTHandler mthandler;
	MOHandler mohandler;
	int defaultbindstatus;

	Date lastpoolingtime = new Date();
	int poolingtime = 30000;

    long lastDateCheckTime;

    int dateCheckInterval = 120000;

	int checktime 	= 120000; 
	boolean run = true;
	boolean exit = false;
	int noofmt = 2;
	int timesleep = 2000;
    int waitInterval = 40;
    int unacknowledgedSends = 0;
    int maxUnacknowledgedSends = 0;

	public Hashtable htMsgid = new Hashtable();
	Logger logger = null;


	public String moqueueName = "";
	String queueName = "";
	public String queueUrl = "";

	public int truncateShortcode = 0;
	String PREMIUM_SENDER = null;
	String REPLACE_SENDER = null;


	BaseReceiverBox<SMSMessagingObj> box;
	IntRespSenderBox responder;
    IntRespSenderBox mobox;

    boolean isTest = false;

    String logLocation = null;
    DataLogger dataLogger = null;
	DRLogger drLogger = null;

	public ClientConnection(Start mainclass, String ip, int port, int bindstatus,
			                String name, Logger logger)
	{
		super(ip, port, name, logger);
		this.mainclass	= mainclass;
		this.logger		= logger;		

		logger.info("Starting ["+name+"] Bind status:"+bindstatus);

		if(bindstatus == STATE_BINDTRANSMITTER){
            mthandler = new MTHandler(this, name, logger);
            mohandler = new MOHandler(this, name, logger);

		}
		if(bindstatus == STATE_BINDRECEIVER){
            mohandler = new MOHandler(this, name, logger);
		}

		defaultbindstatus = bindstatus;
		noofmt		= Integer.parseInt(ConnConfig.getProperty("noofmt"));
		timesleep	= Integer.parseInt(ConnConfig.getProperty("timesleep"));
		state = bindstatus;

		queueUrl = ConnConfig.getProperty("QUEUE_URL");
		queueName = ConnConfig.getProperty("QUEUE_NAME");
		moqueueName = ConnConfig.getProperty("QUEUE_NAME_MO");

        unacknowledgedSends = 0;
        maxUnacknowledgedSends = -1;

        if (ConnConfig.getProperty("MAX_UNACKNOWLEDGED_SEND")!=null) {
            maxUnacknowledgedSends = Integer.parseInt(ConnConfig.getProperty("MAX_UNACKNOWLEDGED_SEND"));
        }

        if (ConnConfig.getProperty("WAIT_INTERVAL")!=null) {
            waitInterval = Integer.parseInt(ConnConfig.getProperty("WAIT_INTERVAL"));
        }

        if (ConnConfig.getProperty("IS_TEST")==null) {
            isTest = false;
        } else {
            isTest = Boolean.parseBoolean(ConnConfig.getProperty("IS_TEST"));
        }

		try{

			REPLACE_SENDER = ConnConfig.getProperty("REPLACE_SENDER");
			PREMIUM_SENDER = ConnConfig.getProperty("PREMIUM_SENDER");
			truncateShortcode	= Integer.parseInt(ConnConfig.getProperty("MO_SHORTCODE_TRUNC"));
		}catch(Exception ex){
			REPLACE_SENDER = null;
			PREMIUM_SENDER = null;
			truncateShortcode = 0;
		}

        logLocation = ConnConfig.getProperty("DATALOG_LOCATION");
        if (logLocation==null) {
            logLocation = "./";
        }

        DataLogger.location = logLocation;
		DRLogger.location = logLocation;

        dataLogger = DataLogger.getInstance();
        drLogger = DRLogger.getInstance();

		lastDateCheckTime = System.currentTimeMillis();

		if ( isTest==true || connect()==true )
		{

			logger.info("Queue ["+queueName+"] |SMPP Connected");

            try {
				if (bindstatus == STATE_BINDTRANSMITTER) {
					box = new SMSReceiverBoxImpl(queueName);
				}
				if (bindstatus == STATE_BINDRECEIVER) {
					mobox = new SMSIntRespSenderBoxImpl(moqueueName);
				}
				if (bindstatus == STATE_BINDTRANSMITTER) {
					responder = new SMSIntRespSenderBoxImpl(Constants.INTERNAL_SMS_RESP_QUEUE);
				}
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            start();

		} else {
			logger.info("Queue [" + queueName + "] |SMPP Not Connected");
			mainclass.reinit();
		}
	}

	public  String formatMsg(String msg, int type, boolean hideDollar)
	{
		//if(msg != null && msg.length() <= 160)

		if(msg != null && type == 1)
		{
			if (!hideDollar && msg.indexOf("$") >= 0) {
				msg = msg.replace('$', (char) 2);
			}
			if (!hideDollar && msg.indexOf("@") >= 0) {
				msg = msg.replace('@', (char) 0);
			}
		}

		logger.info("@@MSG LENGTH[" + msg.length() + "]|MSG[" + msg + "]");
		return msg;
	}

    public int getNoOfParts(SMSMessagingObj msg) {
        return 1;
    }

    public static boolean isPureAscii(String v) {
        byte bytearray []  = v.getBytes();
        CharsetDecoder d = Charset.forName("US-ASCII").newDecoder();
        try {
            CharBuffer r = d.decode(ByteBuffer.wrap(bytearray));
            r.toString();
        }
        catch(CharacterCodingException e) {
            return false;
        }
        return true;
    }

    public void processResponse(int msgSeqId, boolean status) {
        if (unacknowledgedSends>0) {
            unacknowledgedSends--;
            logger.info("UNACKNOWLEDGEDSENDS : "+unacknowledgedSends);
        }

        //TBD
        if (status==false) {
            //write to failed logs
            //remove from id tracker
        } else {
            //remove from id tracker
        }

    }

	public void run(){
		try{
			int waitingtime = 0;

			String tpoa = null;

			while(run){	
				Object inputobj  = null;
				MOObj moobj = null;
				MTObj resp = null;

				if (isTest==true || (socket!=null && reconnect==false) ){
					try{
                        if (isTest==true) {
                            state = STATE_CONNECTASTRANSMITTER;
                        }

						connectionAlive = true;
						switch(state){
						case STATE_BINDTRANSMITTER:
							logger.info(statestr[state]);
							MTObj transmitterobj = mthandler.bindTransmitter();
							addOutputMsg(transmitterobj);
							state = STATE_WAITINGINITRESP;
							break; 

						case STATE_BINDRECEIVER:
							logger.info(statestr[state]);
							MTObj receiverobj = null;

							if(mthandler != null)
							{	
								receiverobj = mthandler.bindReceiver();
							}else
								receiverobj = mohandler.bindReceiver();

							addOutputMsg(receiverobj);
							state = STATE_WAITINGINITRESP;
							break; 

						case STATE_WAITINGINITRESP:
							logger.info(statestr[state]);
							System.out.println(">>>>>> "+mohandler.getBindStatus());
							switch(mohandler.getBindStatus()){
							case BINDSTATUS_BINDTRANSMITTER:
								logger.info("MO:"+statestr[STATE_CONNECTASTRANSMITTER]);
								state = STATE_CONNECTASTRANSMITTER;
								break;
							case BINDSTATUS_BINDRECEIVER:
								logger.info("MO:"+statestr[BINDSTATUS_BINDRECEIVER]);
								state = STATE_CONNECTASRECEIVER;
								break;
							case BINDSTATUS_INIT:
								logger.info("MO: BIND STATUS INIT"); //+statestr[BINDSTATUS_INIT]);
								state = defaultbindstatus;
								break;	
							default:
								logger.info("MO: Default");
								Object respobj = getInputMsg();
								if ( respobj!=null ){
									moobj = (MOObj)respobj;

									resp = mohandler.unpack(moobj);

									if ( resp!=null )
										addOutputMsg(resp);
								}else{
									if ( waitingtime>120 )
										mainclass.reinit();
									waitingtime++;
									sleep(CONN_SLEEPTIME);
								}
								break;
							}
                            //logger.info("MO: Unlocked Sender");
							unlockSender();
                            logger.info("MO: Unlocked Sender");
							break;

						case STATE_CONNECTASTRANSMITTER:
							if ( isTest==false && isConnectionAlive()==false ) {
								System.exit(1);
							}

                            if (System.currentTimeMillis()-lastDateCheckTime>dateCheckInterval) {
                                if (dataLogger.dateHasChanged()) {
                                    dataLogger.reinit();
                                }
								if (drLogger.dateHasChanged()) {
									drLogger.reinit();
								}
                                lastDateCheckTime = System.currentTimeMillis();
                            }

                            if (isTest==true) {
                                logger.info("MT|WAITING FOR MESSAGE");
                                SMSMessagingObj msg = box.receive();
                                if (msg!=null) {
                                    String campName = msg.getCampaignID();
                                    logger.info("MT|FROM QUEUE|" + campName + "|" + msg.toString() + "|MODE: " + msg.getContentType());
                                    dataLogger.log(
											System.currentTimeMillis()+"",
											System.currentTimeMillis(),
                                            msg.getDestinationAddress(),
                                            msg.getSenderAddress(),
                                            msg.getBody(),
                                            getNoOfParts(msg),
                                            msg.getCampaignID(),
                                            msg.getInstanceID(),
                                            msg.getFileLineNo(),
                                            msg.getMsgCode(),
                                            0);
                                    responder.send("success", msg);
                                }
                            } else {
                                try {
                                    if (mthandler.stopServer() == false) {
                                        //logger.info("MT|WAITING FOR MESSAGE");
                                        SMSMessagingObj msg = null;
                                        if (maxUnacknowledgedSends>0 &&
                                            unacknowledgedSends<maxUnacknowledgedSends) {
                                            msg = box.receiveNoWait();
                                        }

                                        if (msg != null) {
                                            tpoa = msg.getSenderAddress();

                                            if (REPLACE_SENDER != null && REPLACE_SENDER.trim().length() > 0) {
                                                tpoa = tpoa.replaceAll(REPLACE_SENDER, "");
                                            }

                                            logger.info("MSG:" + msg.getBody());

                                            String campName = msg.getCampaignID();
                                            //logger.info("MT|FROM QUEUE|" + campName + "|" + msg.toString() + "|MODE: " + msg.getContentType());
                                            //int contentType = msg.getContentType();
                                            int contentType = 3;
                                            String strBody = msg.getBody();

                                            if (isPureAscii(strBody)) {
                                                contentType = 1;
                                            }
                                            logger.info("MT|FROM QUEUE|" + campName + "|" + msg.toString() + "|CONTENTTYPE: " + contentType);
                                            //System.out.println("CONTENT TYPE "+contentType);
                                            long msgId = System.currentTimeMillis();
                                            SendObj asendobj = new SendObj(msgId, msg.getDestinationAddress(),
                                                    formatMsg(msg.getBody(), contentType, isDialogue),
                                                    contentType, tpoa);

                                            MTObj[] mtobjs = mthandler.submitSM(asendobj);
                                            for (int i = 0; i < mtobjs.length; i++) {
                                                if (mtobjs[i] != null) {
                                                    addOutputMsg(mtobjs[i]);
                                                }
                                                if (i != 0 && i % noofmt == 0) {
                                                    //sleep(timesleep);
                                                }
                                            }

                                            unacknowledgedSends=unacknowledgedSends+mtobjs.length;

                                            //sleep(waitInterval);

                                            inputobj = getInputMsg();
                                            if (inputobj != null) {
                                                moobj = (MOObj) inputobj;
                                                resp = mohandler.unpack(moobj);
                                                if (resp != null)
                                                    addOutputMsg(resp);
                                                lastpoolingtime = new Date();
                                            }

                                            dataLogger.log(
													msgId+"",
													System.currentTimeMillis(),
                                                    msg.getDestinationAddress(),
                                                    msg.getSenderAddress(),
                                                    msg.getBody(),
                                                    mtobjs.length,
                                                    msg.getCampaignID(),
                                                    msg.getInstanceID(),
                                                    msg.getFileLineNo(),
                                                    msg.getMsgCode(),
                                                    0);
                                            //System.out.println("Sending Response");
                                            responder.send("success",msg);
                                        } else {
                                            inputobj = getInputMsg();
                                            //System.out.println("inputObj "+inputobj);
                                            if (inputobj != null) {
                                                //System.out.println(">>>>1");
                                                moobj = (MOObj) inputobj;
                                                resp = mohandler.unpack(moobj);
                                                if (resp != null) {
                                                    //System.out.println(">>>>2");
                                                    addOutputMsg(resp);
                                                    //System.out.println(">>>>3");
                                                }
                                                lastpoolingtime = new Date();
                                            } else {
                                                //System.out.println(">>>>4");
                                                Date now = new Date();
                                                if (lastpoolingtime.getTime() + poolingtime < now.getTime()) {
                                                    //System.out.println(">>>>5");
                                                    MTObj sendObj = mthandler.enquireLink();
                                                    //System.out.println(">>>>6");
                                                    addOutputMsg(sendObj);
                                                    //System.out.println(">>>>7");
                                                    lastpoolingtime = now;
                                                } else {
                                                    //System.out.println(">>>>8");
                                                    if ((mohandler.getPoolingDateReceived()).getTime() + (checktime) < now.getTime()) {
                                                        logger.info("\n" + name + ":NO POLLING:" + mohandler.getPoolingDateReceived());
                                                        mainclass.restartReceiver();
                                                        return;
                                                    }
                                                    //System.out.println(">>>>9");
                                                }
                                            }
                                            //sleep(waitInterval);
                                            //sleep(CONN_SLEEPTIME);
                                        }
                                        sleep(waitInterval);
                                    } else {
                                        if (mohandler.stopServer() == false) {
                                            inputobj = getInputMsg();
                                            if (inputobj != null) {
                                                moobj = (MOObj) inputobj;
                                                resp = mohandler.unpack(moobj);
                                            }
                                            sleep(CONN_SLEEPTIME);
                                        } else {
                                            run = false;
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
							break;

						case STATE_CONNECTASRECEIVER:
							if ( isConnectionAlive()==false ){
								System.exit(1);
							}						
							if ( exit==false){
								inputobj = getInputMsg();
								if ( inputobj!=null ){
									moobj = (MOObj)inputobj;
									resp = mohandler.unpack(moobj);
									if ( resp!=null )
										addOutputMsg(resp);
									lastpoolingtime = new Date();
								}else{
									Date now = new Date();
									if ( lastpoolingtime.getTime()+poolingtime<now.getTime() ){
										MTObj sendObj = mohandler.enquireLink();
										addOutputMsg(sendObj);
										lastpoolingtime = now;
									}
									else{
										if (  (mohandler.getPoolingDateReceived()).getTime()+(checktime)<
												now.getTime() ){
											logger.info("\n"+name+":NO POLLING:"+mohandler.getPoolingDateReceived());			
											//dbConn.deleteSequenceNo();
											mainclass.restartReceiver();
											return;
										}	
									}
									sleep(CONN_SLEEPTIME);
								}
							}else{
								logger.info("receiver exit = true");
								if ( mohandler.stopServer()==false ){
									logger.info("dun wait for unbind response");
									sleep(2000);
									run = false;
								}
								else
									run = false;
							}
							break;
						}
					}
					catch(Exception e){
						logger.error("Exception", e);
						e.printStackTrace();
						break;
					}
				}else{
					mainclass.reinit();
				}

			}
			disconnect();
			mainclass.unlock();
			mainclass.exit();			
		}catch(Exception e){
			logger.error("Exception @ Run", e);
		}
		System.exit(1);
	}

	public void exit(){
		MTObj sendObj = mthandler.unbind();
		addOutputMsg(sendObj);
		exit = true;
	}

	public synchronized int getSequenceNo(){
		if ( sequencenumber>=0x07FFFFFFF ){
			sequencenumber = 1;
			return sequencenumber;
		}
		return (sequencenumber++);
	}

	public boolean isUniqueSequenceNo(String strseq){
		try{
			int seq = Integer.parseInt(strseq);
			return true;
		}catch(Exception e){}
		return false;
	}
}

