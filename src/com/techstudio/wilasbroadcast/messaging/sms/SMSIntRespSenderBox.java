package com.techstudio.wilasbroadcast.messaging.sms;

public interface SMSIntRespSenderBox {
  void send(String status, SMSMessagingObj obj);
}
