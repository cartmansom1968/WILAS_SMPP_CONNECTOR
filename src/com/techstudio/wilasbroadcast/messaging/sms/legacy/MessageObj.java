package com.techstudio.wilasbroadcast.messaging.sms.legacy;

import java.io.Serializable;
import java.util.Hashtable;

public class MessageObj extends SuperMessageObj implements Serializable {
  private static final long serialVersionUID = 8738971025043014934L;
  public String[] destinationAddress;
  public int agentID;
  public int credit;
  public int status;
  public String remark;

  public String getMsisdn() {
    return this.destinationAddress[0];
  }

  public double getPrice() {
    return 0.0D;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }

  public String toString() {
    return this.contentTypeID != 1 && this.contentTypeID != 2?(this.contentTypeID != 8 && this.contentTypeID != 64?(this.contentTypeID == 4?"MMS|MSISDN: " + this.destinationAddress[0] + "|SUBJECT: " + this.getSubject() + "|SENDER: " + this.getSender() + "|CONTENTTYPEID: " + this.contentTypeID:"UNKNOWN CONTENT TYPE "):"WAP|MSISDN: " + this.destinationAddress[0] + "|BODY: " + this.getBody() + "|URL: " + this.getURL() + "|SENDER: " + this.getSender() + "|CONTENTTYPEID: " + this.contentTypeID):"SMS|MSISDN: " + this.destinationAddress[0] + "|BODY: " + this.getBody() + "|SENDER: " + this.getSender() + "|CONTENTTYPEID: " + this.contentTypeID;
  }

  public MessageObj(long messageId, String destination, String originator, int contentType, String message) {
    this.status = -1;
    this.remark = null;
    this.messageID = messageId;
    this.destinationAddress = new String[]{destination};
    Hashtable messageTable = new Hashtable();
    messageTable.put("SMS_BODY", message);
    messageTable.put("SMS_SENDER", originator);
    this.messageTable = messageTable;
    this.contentTypeID = contentType;
  }

  public MessageObj(long messageID, String[] destinationAddress, long campaignID, int agentID, int contentTypeID, int credit, Hashtable messageTable) {
    this.status = -1;
    this.remark = null;
    this.destinationAddress = destinationAddress;
    this.campaignID = campaignID;
    this.agentID = agentID;
    this.contentTypeID = contentTypeID;
    this.messageTable = messageTable;
    this.messageID = messageID;
    this.credit = credit;
  }

  private MessageObj(long messageID, String[] destinationAddress, long campaignID, int agentID, int contentTypeID, int credit, Hashtable messageTable, int status, String remark) {
    this(messageID, destinationAddress, campaignID, agentID, contentTypeID, credit, messageTable);
    this.status = status;
    this.remark = remark;
  }

  public MessageObj(long messageID, String[] destinationAddress, long campaignID, int agentID, int contentTypeID, int credit, Hashtable messageTable, short mode) {
    this(messageID, destinationAddress, campaignID, agentID, contentTypeID, credit, messageTable);
    this.mode = mode;
  }

  public MessageObj(long messageID, String[] destinationAddress, long campaignID, int agentID, int contentTypeID, int credit, Hashtable messageTable, int status, String remark, short mode) {
    this(messageID, destinationAddress, campaignID, agentID, contentTypeID, credit, messageTable);
    this.status = status;
    this.remark = remark;
    this.mode = mode;
  }

  public long getMessageID() {
    return this.messageID;
  }

  public void setMessageID(long messageID) {
    this.messageID = messageID;
  }

  public String[] getDestinationAddress() {
    return this.destinationAddress;
  }

  public void setDestinationAddress(String[] destinationAddress) {
    this.destinationAddress = destinationAddress;
  }

  public long getCampaignID() {
    return this.campaignID;
  }

  public void setCampaignID(long campaignID) {
    this.campaignID = campaignID;
  }

  public int getAgentID() {
    return this.agentID;
  }

  public void setAgentID(int agentID) {
    this.agentID = agentID;
  }

  public int getContentTypeID() {
    return this.contentTypeID;
  }

  public void setContentTypeID(int contentTypeID) {
    this.contentTypeID = contentTypeID;
  }

  public int getCredit() {
    return this.credit;
  }

  public void setCredit(int credit) {
    this.credit = credit;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Hashtable getMessageTable() {
    return this.messageTable;
  }

  public void setMessageTable(Hashtable messageTable) {
    this.messageTable = messageTable;
  }
}
