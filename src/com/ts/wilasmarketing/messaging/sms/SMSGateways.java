package com.ts.wilasmarketing.messaging.sms;


import com.ts.wilasmarketing.messaging.BaseReceiverBox;
import com.ts.wilasmarketing.messaging.BaseSenderBox;
import com.ts.wilasmarketing.messaging.IntRespSenderBox;
import com.ts.wilasmarketing.setup.SMSResource;
import com.ts.wilasmarketing.sms.SMSRecord;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class SMSGateways {

  private static SMSGateways theInstance;

  ConcurrentHashMap<String, BaseSenderBox<SMSRecord>> boxes;

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
    boxes = new ConcurrentHashMap<String, BaseSenderBox<SMSRecord>>();
  }

  // TODO: need to support creation of multiple boxes with same session?
  private BaseSenderBox<SMSRecord> createSenderBox(SMSResource resource) {
    BaseSenderBox<SMSRecord> box = new SMSSenderBoxJMSImpl(resource.getConnectorId());
//    BaseSenderBox<SMSRecord> box = new SMSFakeSenderBox();
    boxes.put(resource.getConnectorId(), box);
    return box;
  }

  public BaseSenderBox<SMSRecord> getSenderBox(SMSResource resource) {
    BaseSenderBox<SMSRecord> box = boxes.get(resource.getConnectorId());
    if (box == null) {
      return createSenderBox(resource);
    }
    return box;
  }

  public void removeSenderBox(SMSResource resource) {
    if (resource == null) {
      logger.info("no such resource ");
      return;
    }
    BaseSenderBox<SMSRecord> box = boxes.remove(resource.getConnectorId());
    if (box == null) {
      logger.debug("No such sender box " + resource);
    }
    else {
      logger.info("sender box removed " + resource);
    }
  }

  public IntRespSenderBox createIntRespSender(String name) {
    SMSIntRespSenderBoxImpl box = new SMSIntRespSenderBoxImpl(name);
    return box;
  }

  public BaseReceiverBox createIntRespReceiver(String name) {
    SMSIntRespReceiverBoxImpl box = new SMSIntRespReceiverBoxImpl(name);
    return box;
  }

}
