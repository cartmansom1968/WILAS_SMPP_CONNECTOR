package com.techstudio.wilasbroadcast.setup;

public class SMSResource {
  private String connectorId;
  private String shortCode;
  private String keyword;

  public SMSResource() {
  }

  @Override
  public String toString() {
    return "SMSResource{" +
            "connectorId='" + connectorId + '\'' +
            ", shortCode='" + shortCode + '\'' +
            ", keyword='" + keyword + '\'' +
            '}';
  }

  public String getConnectorId() {
    return connectorId;
  }

  public void setConnectorId(String connectorId) {
    this.connectorId = connectorId;
  }

  public String getShortCode() {
    return shortCode;
  }

  public void setShortCode(String shortCode) {
    this.shortCode = shortCode;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
}
