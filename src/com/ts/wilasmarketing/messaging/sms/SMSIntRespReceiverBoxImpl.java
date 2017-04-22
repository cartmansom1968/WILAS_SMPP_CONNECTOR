package com.ts.wilasmarketing.messaging.sms;

import com.techstudio.wilasbroadcast.messaging.sms.*;
import com.ts.wilasmarketing.messaging.BaseReceiverBox;
import com.ts.wilasmarketing.messaging.ReceiverBoxImpl;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

public class SMSIntRespReceiverBoxImpl extends ReceiverBoxImpl implements BaseReceiverBox {


  public SMSIntRespReceiverBoxImpl(String name) {
    super(name);
  }

  @Override
  public SMSInternalResponseObj receive() {

    try {
      return (SMSInternalResponseObj) consumer.receive().getObject();
    } catch (JMSException e) {
      e.printStackTrace();
    }
    return null;
  }

  public SMSInternalResponseObj receiveNoWait() {
    try {
      ObjectMessage msg = (ObjectMessage) consumer.receiveNoWait();
      if (msg!=null) {
        return (SMSInternalResponseObj) msg.getObject();
      }
    } catch (JMSException e) {
      e.printStackTrace();
    }
    return null;
  }
}
