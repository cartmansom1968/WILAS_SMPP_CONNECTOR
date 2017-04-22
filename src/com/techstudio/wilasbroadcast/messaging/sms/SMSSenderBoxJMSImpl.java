package com.techstudio.wilasbroadcast.messaging.sms;

import com.techstudio.wilasbroadcast.messaging.JMSFactory;
import com.techstudio.wilasbroadcast.sms.SMSRecord;

public class SMSSenderBoxJMSImpl implements SMSSenderBox {

  JMSFactory.JMSProducer producer;

  public SMSSenderBoxJMSImpl(String name) {
    this.producer = new JMSFactory.JMSProducer(name);
  }

  @Override
  public void send(SMSRecord message) {

    SMSMessagingObj messagingObj = new
            SMSMessagingObj(message.getTpoa(), message.getDestMSISDN(), message.getContent(), 1, message.getMessageCode());

    messagingObj.setCampaignID(message.getCampaign().getId());
    messagingObj.setInstanceID(message.getCampaign().getInstanceId());
    messagingObj.setFileLineNo(message.getFileLineNo());

    producer.send(messagingObj);


  }
}
