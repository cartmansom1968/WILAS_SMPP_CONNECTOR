package com.techstudio.wilasbroadcast.messaging.sms;

import com.techstudio.wilasbroadcast.messaging.JMSFactory;

import javax.jms.JMSException;

public class SMSIntRespReceiverBoxImpl implements SMSIntRespReceiverBox {

  JMSFactory.JMSConsumer consumer;

  public SMSIntRespReceiverBoxImpl(String name) {
    this.consumer = new JMSFactory.JMSConsumer(name);
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
}
