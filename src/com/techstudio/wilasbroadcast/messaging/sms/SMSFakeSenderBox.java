package com.techstudio.wilasbroadcast.messaging.sms;

import com.techstudio.wilasbroadcast.sms.SMSRecord;

public class SMSFakeSenderBox implements SMSSenderBox {

  @Override
  public void send(SMSRecord message) {
    System.out.println(message + " jms sent ");
  }
}
