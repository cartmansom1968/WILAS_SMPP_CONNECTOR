package com.ts.wilasmarketing.data;

public enum BroadcastRecordType {

  SMS("SMS"),
  EMAIL("EMAIL"),
  NOTIFICATION("NOTIFICATION"),
  APNS_NOTIFICATION("APNS_NOTIFICATION"),
  GCM_NOTIFICATION("GCM_NOTIFICATION");

  private String desc;

  BroadcastRecordType(String desc) {
    this.desc = desc;
  }


  static BroadcastRecordType getType(String desc) {
    for (BroadcastRecordType type : values()) {
      if (type.desc.equalsIgnoreCase(desc)) return type;
    }
    return null;
  }


  public String getDesc() {
    return desc;
  }
}
