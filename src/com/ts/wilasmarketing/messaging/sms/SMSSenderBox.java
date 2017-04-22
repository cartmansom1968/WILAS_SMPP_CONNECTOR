package com.ts.wilasmarketing.messaging.sms;

import com.techstudio.wilasbroadcast.sms.SMSRecord;

public interface SMSSenderBox {
  void send(SMSRecord message);
}
