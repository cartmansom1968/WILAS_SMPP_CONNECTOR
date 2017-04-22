package com.techstudio.wilasbroadcast.sms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SMSMappingFile {
  HashMap<String, String> mappings;

  public SMSMappingFile(String fileName) {
    mappings = new HashMap<String, String>();
    init(fileName);
  }

  private void init(String fileName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));

      String line = null;
      while ((line = reader.readLine()) != null) {
        if (line.indexOf(',') > 0) {
          String key = line.substring(0, line.indexOf(',')).trim();
          String val = line.substring(line.indexOf(',')+1).trim();
          if (key.length() > 0 && val.length() > 0) {
            mappings.put(key, val);
          }
          else {
            System.out.println(" template line rejected " + line);
          }
        }
      }

    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getValue(String key) {
    return mappings.get(key);
  }

  public static void main(String[] args) {
    SMSMappingFile templateFile = new SMSMappingFile("input/IRASFixedSMSTemplate.cfg");

    String val = templateFile.getValue("IRAS1");
    System.out.println(val);
    val = templateFile.getValue("IRAS2");
    System.out.println(val);
    val = templateFile.getValue("IRAS3");
    System.out.println(val);
  }
}
