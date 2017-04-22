package com.techstudio.wilasbroadcast.messaging.sms.legacy;

import java.io.Serializable;
import java.util.Hashtable;

public abstract class SuperMessageObj implements Serializable {
  private static final long serialVersionUID = 309525347112076654L;
  public long messageID;
  public long campaignID = 0L;
  public Hashtable messageTable = null;
  public int contentTypeID;
  public byte messageType = 0;
  public short mode = 0;
  public boolean broadcast = false;
  public int flag = -1;

  public SuperMessageObj() {
  }

  public int getFlag() {
    return this.flag;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }

  public abstract String toString();

  public abstract String getMsisdn();

  public abstract double getPrice();

  public Hashtable getMessageTable() {
    return this.messageTable;
  }

  public long getMessageID() {
    return this.messageID;
  }

  public long getCampaignID() {
    return this.campaignID;
  }

  public int getContentTypeID() {
    return this.contentTypeID;
  }

  public void setBody(String body) {
    if(this.contentTypeID != 1 && this.contentTypeID != 2) {
      if(this.contentTypeID == 8 || this.contentTypeID == 64) {
        this.messageTable.put("WAP_BODY", body);
      }
    } else {
      this.messageTable.put("SMS_BODY", body);
    }

  }

  public String getBody() {
    String body = "";
    if(this.contentTypeID != 1 && this.contentTypeID != 2) {
      if(this.contentTypeID == 8 || this.contentTypeID == 64) {
        body = this.messageTable.get("WAP_BODY") == null?"":this.messageTable.get("WAP_BODY").toString();
      }
    } else {
      body = this.messageTable.get("SMS_BODY") == null?"":this.messageTable.get("SMS_BODY").toString();
    }

    return body;
  }

  public String getSender() {
    String sender = "";
    if(this.contentTypeID != 1 && this.contentTypeID != 2) {
      if(this.contentTypeID != 8 && this.contentTypeID != 64) {
        if(this.contentTypeID == 4) {
          sender = this.messageTable.get("MMS_SENDER") == null?"":this.messageTable.get("MMS_SENDER").toString();
        }
      } else {
        sender = this.messageTable.get("WAP_SENDER") == null?"":this.messageTable.get("WAP_SENDER").toString();
      }
    } else {
      sender = this.messageTable.get("SMS_SENDER") == null?"":this.messageTable.get("SMS_SENDER").toString();
    }

    return sender;
  }

  public String getURL() {
    String URL = "";
    if(this.contentTypeID == 8 || this.contentTypeID == 64) {
      URL = this.messageTable.get("WAP_URL") == null?"":this.messageTable.get("WAP_URL").toString();
    }

    return URL;
  }

  public String getSubject() {
    String subject = "";
    if(this.contentTypeID == 4) {
      subject = this.messageTable.get("MMS_SUBJECT") == null?"":this.messageTable.get("MMS_SUBJECT").toString();
    }

    return subject;
  }

  public int getMsgCount() {
    int var11;
    int var12;
    if(this.contentTypeID == 1) {
      var11 = this.messageTable.get("SMS_BODY").toString().length();
      if(var11 == 0) {
        return 0;
      } else if(var11 <= 160) {
        return 1;
      } else {
        var12 = var11 / 153;
        if(var11 % 153 != 0) {
          ++var12;
        }

        return var12;
      }
    } else if(this.contentTypeID == 2) {
      var11 = this.messageTable.get("SMS_BODY").toString().length();
      if(var11 == 0) {
        return 0;
      } else if(var11 <= 67) {
        return 1;
      } else {
        var12 = var11 / 67;
        if(var11 % 67 != 0) {
          ++var12;
        }

        return var12;
      }
    } else if(this.contentTypeID != 8) {
      return 1;
    } else {
      String url = this.messageTable.get("WAP_URL").toString();
      String title = this.messageTable.get("WAP_BODY").toString();
      String strseqid = "01";
      String part1 = strseqid + "0601AE" + "02056A0045C60B03";
      String part2 = "00070103";
      String part3 = "000101";
      String hexMessage = "";
      String hexURL = "";

      int body;
      char count;
      for(body = 0; body < url.length(); ++body) {
        count = url.charAt(body);
        hexURL = hexURL + Integer.toHexString(count);
      }

      for(body = 0; body < title.length(); ++body) {
        count = title.charAt(body);
        hexMessage = hexMessage + Integer.toHexString(count);
      }

      String var13 = part1 + hexURL.toUpperCase() + part2 + hexMessage.toUpperCase() + part3;
      if(var13.length() <= 266) {
        return 1;
      } else {
        int var14 = var13.length() / 256;
        if(var13.length() % 256 != 0) {
          ++var14;
        }

        return var14;
      }
    }
  }

  public short getMode() {
    return this.mode;
  }

  public void setMode(short mode) {
    this.mode = mode;
  }

  public boolean isBroadcast() {
    return this.broadcast;
  }

  public void setBroadcast(boolean broadcast) {
    this.broadcast = broadcast;
  }
}
