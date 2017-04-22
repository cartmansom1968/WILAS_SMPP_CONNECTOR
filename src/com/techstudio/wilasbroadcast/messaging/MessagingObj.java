package com.techstudio.wilasbroadcast.messaging;

import com.techstudio.wilasbroadcast.messaging.sms.SMSMessagingObj;

import java.io.Serializable;

public class MessagingObj implements Serializable {
  private static final long serialVersionUID = 7473625773945740701L;

  public String campaignID = null;
  public String instanceID = null;
  public long fileLineNo;

  public MessagingObj() {
  }


  public void copy(SMSMessagingObj obj) {
    this.campaignID = obj.campaignID;
    this.instanceID = obj.instanceID;
    this.fileLineNo = obj.fileLineNo;
  }


  @Override
  public String toString() {
    return "MessagingObj{" +
            "campaignID='" + campaignID + '\'' +
            ", instanceID='" + instanceID + '\'' +
            ", fileLineNo=" + fileLineNo +
            '}';
  }

  public String getCampaignID() {
    return campaignID;
  }

  public void setCampaignID(String campaignID) {
    this.campaignID = campaignID;
  }

  public String getInstanceID() {
    return instanceID;
  }

  public void setInstanceID(String instanceID) {
    this.instanceID = instanceID;
  }

  public long getFileLineNo() {
    return fileLineNo;
  }

  public void setFileLineNo(long fileLineNo) {
    this.fileLineNo = fileLineNo;
  }
}
