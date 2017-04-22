package com.ts.wilasmarketing.setup;

public class CampaignConfig {
  private String id;
  private SMSResource smsResource;

  public CampaignConfig() {
  }

  @Override
  public String toString() {
    return "CampaignConfig{" +
            "id='" + id + '\'' +
            ", smsResource=" + smsResource +
            '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public SMSResource getSmsResource() {
    return smsResource;
  }

  public void setSmsResource(SMSResource smsResource) {
    this.smsResource = smsResource;
  }
}
