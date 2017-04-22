package com.techstudio.wilasbroadcast.messaging.sms;

import com.techstudio.wilasbroadcast.messaging.JMSFactory;

public class SMSIntRespSenderBoxImpl implements SMSIntRespSenderBox {

  JMSFactory.JMSProducer producer;

  public SMSIntRespSenderBoxImpl(String name) {
    this.producer = new JMSFactory.JMSProducer(name);
  }

  @Override
  public void send(String status, SMSMessagingObj obj) {

    SMSInternalResponseObj response = new SMSInternalResponseObj(status, obj.getMsgCode());
    response.copy(obj);
    producer.send(response);

  }
}
