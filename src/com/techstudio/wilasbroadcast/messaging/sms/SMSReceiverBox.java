package com.techstudio.wilasbroadcast.messaging.sms;

public interface SMSReceiverBox {
  SMSMessagingObj receive();
  SMSMessagingObj receiveNoWait();
}
