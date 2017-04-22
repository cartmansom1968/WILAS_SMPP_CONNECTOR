package com.ts.wilasmarketing.messaging.sms;

import com.ts.wilasmarketing.messaging.BaseSenderBox;
import com.ts.wilasmarketing.sms.SMSRecord;

public class SMSFakeSenderBox implements BaseSenderBox<SMSRecord> {

  @Override
  public void send(SMSRecord message) {
    System.out.println(message + " jms sent ");
  }
}
