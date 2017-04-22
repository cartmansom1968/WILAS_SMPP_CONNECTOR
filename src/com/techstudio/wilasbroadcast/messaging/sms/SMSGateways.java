package com.techstudio.wilasbroadcast.messaging.sms;


import com.techstudio.wilasbroadcast.setup.SMSResource;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class SMSGateways {

  public static final String INTERNAL_RESP_QUEUE = "internalRespQueue";

  private static SMSGateways theInstance;

  ConcurrentHashMap<String, SMSSenderBox> boxes;

  static Logger logger = Logger.getLogger(SMSGateways.class);

  static public SMSGateways getInstance() {
    if (theInstance == null) {
      synchronized (SMSGateways.class) {
        if (theInstance == null) {
          theInstance = new SMSGateways();
        }
      }
    }
    return theInstance;
  }

  public SMSGateways() {
    boxes = new ConcurrentHashMap<String, SMSSenderBox>();
  }

  // TODO: need to support creation of multiple boxes with same session?
  private SMSSenderBox createSenderBox(SMSResource resource) {
    SMSSenderBox box = new SMSSenderBoxJMSImpl(resource.getConnectorId()+"queue");
//    SMSSenderBox box = new SMSFakeSenderBox();
    boxes.put(resource.getConnectorId(), box);
    return box;
  }

  public SMSSenderBox getSenderBox(SMSResource resource) {
    SMSSenderBox box = boxes.get(resource.getConnectorId());
    if (box == null) {
      return createSenderBox(resource);
    }
    return box;
  }

  public void removeSenderBox(SMSResource resource) {
    SMSSenderBox box = boxes.remove(resource.getConnectorId());
    if (box == null) {
      logger.debug("No such sender box " + resource);
    }
    else {
      logger.info("sender box removed " + resource);
    }
  }

  public SMSIntRespSenderBox createIntRespSender(String name) {
    SMSIntRespSenderBoxImpl box = new SMSIntRespSenderBoxImpl(name);
    return box;
  }

  public SMSIntRespReceiverBox createIntRespReceiver(String name) {
    SMSIntRespReceiverBoxImpl box = new SMSIntRespReceiverBoxImpl(name);
    return box;
  }

}
