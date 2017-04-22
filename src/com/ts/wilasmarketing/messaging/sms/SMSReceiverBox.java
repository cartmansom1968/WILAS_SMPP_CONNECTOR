package com.ts.wilasmarketing.messaging.sms;

import com.techstudio.wilasbroadcast.messaging.sms.SMSMessagingObj;

public interface SMSReceiverBox {
  SMSMessagingObj receive();
  SMSMessagingObj receiveNoWait();
}
