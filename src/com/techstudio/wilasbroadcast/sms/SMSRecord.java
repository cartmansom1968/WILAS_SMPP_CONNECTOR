package com.techstudio.wilasbroadcast.sms;

import com.techstudio.wilasbroadcast.data.BroadcastRecord;
import com.techstudio.wilasbroadcast.data.BroadcastRecordType;

public class SMSRecord extends BroadcastRecord {
  String tpoa;
  String destMSISDN;
  String content;
  String messageCode;

  public SMSRecord(long id, long lineNo, String tpoa, String msisdn, String content, String messageCode) {
    super(id, lineNo, BroadcastRecordType.SMS);
    this.tpoa = tpoa;
    this.destMSISDN = msisdn;
    this.content = content;
    this.messageCode = messageCode;

  }

  public SMSRecord(String tpoa, String msisdn, String content) {
    super(-1, -1, BroadcastRecordType.SMS);
    this.tpoa = tpoa;
    this.destMSISDN = msisdn;
    this.content = content;

  }

  @Override
  public String toString() {
    return "SMSRecord{" +
            "tpoa='" + tpoa + '\'' +
            ", destMSISDN='" + destMSISDN + '\'' +
            ", content='" + content + '\'' +
            ", messageCode='" + messageCode + '\'' +
            '}';
  }

  public SMSRecord() {
    super(-1, -1, BroadcastRecordType.SMS);
  }

  public String getTpoa() {
    return tpoa;
  }

  public void setTpoa(String tpoa) {
    this.tpoa = tpoa;
  }

  public String getDestMSISDN() {
    return destMSISDN;
  }

  public void setDestMSISDN(String destMSISDN) {
    this.destMSISDN = destMSISDN;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getMessageCode() {
    return messageCode;
  }

  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }
}
