package com.techstudio.wilasbroadcast.data;

import com.techstudio.wilasbroadcast.Campaign;

public class BroadcastRecord {
  long id;
  long fileLineNo;

  BroadcastRecordType type;

  Campaign campaign;

  public BroadcastRecord() {
  }

  public BroadcastRecord(long id, long fileLineNo, BroadcastRecordType type) {
    this.id = id;
    this.fileLineNo = fileLineNo;
    this.type = type;
  }

  @Override
  public String toString() {
    return "BroadcastRecord{" +
            "id=" + id +
            ", fileLineNo=" + fileLineNo +
            ", type=" + type +
            ", campaign=" + campaign +
            '}';
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getFileLineNo() {
    return fileLineNo;
  }

  public void setFileLineNo(long fileLineNo) {
    this.fileLineNo = fileLineNo;
  }

  public BroadcastRecordType getType() {
    return type;
  }

  public Campaign getCampaign() {
    return campaign;
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
  }
}
