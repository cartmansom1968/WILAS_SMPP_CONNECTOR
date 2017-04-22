package com.techstudio.wilasbroadcast.messaging.sms;

import com.techstudio.wilasbroadcast.sms.SMSRecord;

public interface SMSSenderBox {
  void send(SMSRecord message);
}
