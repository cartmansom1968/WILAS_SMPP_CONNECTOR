package com.techstudio.wilasbroadcast.messaging.sms;

import com.techstudio.wilasbroadcast.messaging.MessagingObj;

import java.io.Serializable;

public class SMSMessagingObj extends MessagingObj implements Serializable {
  private static final long serialVersionUID = -4696727213813578888L;

  public String senderAddress = null;
  public String destinationAddress = null;
  public String body;
  public int contentType;
  public String msgCode;

//  public int contentTypeID;
//  public byte messageType = 0;

  public SMSMessagingObj(String senderAddress, String destinationAddress, String body, int contentType, String msgCode) {
    this.senderAddress = senderAddress;
    this.destinationAddress = destinationAddress;
    this.body = body;
    this.msgCode = msgCode;
    this.contentType = contentType;

  }

  @Override
  public String toString() {
    return "SMSMessagingObj{" +
            "senderAddress='" + senderAddress + '\'' +
            ", destinationAddress='" + destinationAddress + '\'' +
            ", body='" + body + '\'' +
            ", contentType=" + contentType +
            ", msgCode='" + msgCode + '\'' +
            ", base='" + super.toString() + '\'' +
            '}';
  }

  public SMSMessagingObj() {
  }

  public String getSenderAddress() {
    return senderAddress;
  }

  public void setSenderAddress(String senderAddress) {
    this.senderAddress = senderAddress;
  }

  public String getDestinationAddress() {
    return destinationAddress;
  }

  public void setDestinationAddress(String destinationAddress) {
    this.destinationAddress = destinationAddress;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }


  public void setMsgCode(String msgCode) {
    this.msgCode = msgCode;
  }

  public void setContentType(int contentType) {

    this.contentType = contentType;
  }

  public int getContentType() {
    return contentType;
  }

  public String getMsgCode() {
    return msgCode;
  }

}
