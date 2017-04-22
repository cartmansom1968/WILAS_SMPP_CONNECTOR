package com.techstudio.wilasbroadcast.messaging.sms;

import com.techstudio.wilasbroadcast.messaging.JMSFactory;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

public class SMSReceiverBoxImpl implements SMSReceiverBox {

  JMSFactory.JMSConsumer consumer;

  public SMSReceiverBoxImpl(String name) {
    this.consumer = new JMSFactory.JMSConsumer(name);
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
    try {
      ObjectMessage msg = (ObjectMessage) consumer.receiveNoWait();
      if (msg!=null) {
        return (SMSMessagingObj) msg.getObject();
      }
    } catch (JMSException e) {
      e.printStackTrace();
    }
    return null;
  }
}
