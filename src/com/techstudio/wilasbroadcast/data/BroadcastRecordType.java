package com.techstudio.wilasbroadcast.data;

public enum BroadcastRecordType {

  SMS("SMS"),
  EMAIL("EMAIL"),
  NOTIFICATION("NOTIFICATION");

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
