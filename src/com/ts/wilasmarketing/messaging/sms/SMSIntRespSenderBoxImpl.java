package com.ts.wilasmarketing.messaging.sms;

import com.ts.wilasmarketing.messaging.BoxException;
import com.ts.wilasmarketing.messaging.IntRespSenderBox;
import com.ts.wilasmarketing.messaging.SenderBoxImpl;

public class SMSIntRespSenderBoxImpl extends SenderBoxImpl implements IntRespSenderBox<SMSMessagingObj> {

  public SMSIntRespSenderBoxImpl(String name) {
    super(name);
  }

  @Override
  public void send(String status, SMSMessagingObj obj) {

    SMSInternalResponseObj response = new SMSInternalResponseObj(status, obj.getMsgCode());
    response.copy(obj);
    try {
      // TODO: review
      producer.send(response);
    } catch (BoxException e) {
      e.printStackTrace();
    }

  }
}
