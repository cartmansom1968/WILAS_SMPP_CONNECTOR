package com.ts.wilasmarketing.messaging.sms;

import com.ts.wilasmarketing.messaging.BaseReceiverBox;
import com.ts.wilasmarketing.messaging.ReceiverBoxImpl;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

public class SMSReceiverBoxImpl extends ReceiverBoxImpl implements BaseReceiverBox<SMSMessagingObj> {


  public SMSReceiverBoxImpl(String name) {
    super(name);
  }

  @Override
  public SMSMessagingObj receive() {

    try {
      return (SMSMessagingObj) consumer.receive().getObject();
    } catch (JMSException e) {
      e.printStackTrace();
    }
    return null;
  }

  public SMSMessagingObj receiveNoWait() {
      SMSMessagingObj msg = (SMSMessagingObj) consumer.receiveNoWait();
      if (msg!=null) {
        return msg;
      }
      return null;
  }
}
