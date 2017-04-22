package com.ts.wilasmarketing.messaging.sms;

import com.ts.wilasmarketing.messaging.BaseSenderBox;
import com.ts.wilasmarketing.messaging.BoxException;
import com.ts.wilasmarketing.messaging.SenderBoxImpl;
import com.ts.wilasmarketing.sms.SMSRecord;

public class SMSSenderBoxJMSImpl extends SenderBoxImpl implements BaseSenderBox<SMSRecord> {

  public SMSSenderBoxJMSImpl(String name) {
    super(name);
  }

  @Override
  public void send(SMSRecord message) {

    SMSMessagingObj messagingObj = new
            SMSMessagingObj(message.getTpoa(), message.getDestMSISDN(), message.getContent(), 1, message.getMessageCode());

    messagingObj.setCampaignID(message.getCampaign().getId());
    messagingObj.setInstanceID(message.getCampaign().getInstanceId());
    messagingObj.setFileLineNo(message.getFileLineNo());

    try {
      // TODO: to review
      producer.send(messagingObj);
    } catch (BoxException e) {
      e.printStackTrace();
    }


  }
}
