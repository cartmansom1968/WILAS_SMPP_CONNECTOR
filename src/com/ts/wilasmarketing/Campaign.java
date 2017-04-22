package com.ts.wilasmarketing;

import com.ts.wilasmarketing.base.Utils;
import com.ts.wilasmarketing.setup.SMSResource;

import java.util.HashMap;

public class Campaign {
  String id;
  String instanceId;
  String name;

  String iosName;
  String androidName;

  String inputFileName;
  String smsMappingFileName;


  SMSResource smsResource;

  public Campaign() {
  }

  public Campaign(String id, String instanceId, String name) {
    this.id = id;
    this.instanceId = instanceId;
    this.name = name;
  }

  public String getKey() {
    return Utils.getCampaignKey(this.id, this.instanceId);
  }

  @Override
  public String toString() {
    return "Campaign{" +
            "id='" + id + '\'' +
            ", instanceId='" + instanceId + '\'' +
            ", name='" + name + '\'' +
            ", iosName='" + iosName + '\'' +
            ", androidName='" + androidName + '\'' +
            ", inputFileName='" + inputFileName + '\'' +
            ", smsMappingFileName='" + smsMappingFileName + '\'' +
            ", smsResource=" + smsResource +
            '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIosName() {
    return iosName;
  }

  public void setIosName(String iosName) {
    this.iosName = iosName;
  }

  public String getAndroidName() {
    return androidName;
  }

  public void setAndroidName(String androidName) {
    this.androidName = androidName;
  }

  public String getInputFileName() {
    return inputFileName;
  }

  public void setInputFileName(String inputFileName) {
    this.inputFileName = inputFileName;
  }

  public String getSmsMappingFileName() {
    return smsMappingFileName;
  }

  public void setSmsMappingFileName(String smsMappingFileName) {
    this.smsMappingFileName = smsMappingFileName;
  }

  public SMSResource getSmsResource() {
    return smsResource;
  }

  public void setSmsResource(SMSResource smsResource) {
    this.smsResource = smsResource;
  }

  static class SMSTemplate {

    HashMap<String, String> mappings;

    public SMSTemplate() {
      mappings = new HashMap<String, String>();
    }

    void populate(String smsTemplateFileName) {

    }

    String getContent(String sender) {
      return mappings.get(sender);
    }



  }


}
