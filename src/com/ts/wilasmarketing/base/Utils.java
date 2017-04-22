package com.ts.wilasmarketing.base;

import java.io.BufferedReader;
import java.io.FileReader;

public class Utils {

  static String getConnectorIdBasedOnShortCode() {
    return null;
  }

  static String getConnectorIdBasedOnShortCode(String keyword) {
    return null;
  }

  static public String getFileContent(String fileName) {
    String jsonRep = "";
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      String line = null;
      while ((line = reader.readLine()) != null) {
        jsonRep += line;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return jsonRep;
  }

  public static String getCampaignKey(String campaignId, String instanceId) {
    return campaignId + "-" + instanceId;
  }

}
