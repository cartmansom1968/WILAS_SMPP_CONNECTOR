package com.ts.wilasmarketing.messaging.sms;

import com.ts.wilasmarketing.messaging.MessagingObj;

import java.io.Serializable;

public class SMSInternalResponseObj extends MessagingObj implements Serializable {
  private static final long serialVersionUID = 3381705641687899100L;

  private String status;
  private String messageCode;

  public SMSInternalResponseObj() {
  }

  public SMSInternalResponseObj(String status) {
    this.status = status;
  }

  public SMSInternalResponseObj(String status, String messageCode) {
    this.status = status;
    this.messageCode = messageCode;
  }

  public String getMessageCode() {
    return messageCode;
  }

  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
