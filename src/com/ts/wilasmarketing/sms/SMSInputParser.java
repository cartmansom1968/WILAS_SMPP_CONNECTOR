package com.ts.wilasmarketing.sms;

import java.util.StringTokenizer;

/**
 * Created by Daniel on 11/4/2016.
 */
public class SMSInputParser {

  public static final String FIXED = "FIXED";
  public static final String VARIABLE = "VARIABLE";

  private SMSMappingFile templateFile;

  public SMSInputParser() {
  }

  public void setTemplateFile(SMSMappingFile templateFile) {
    this.templateFile = templateFile;
  }

  public SMSRecord parse(String line, long lineNo) {

    StringTokenizer tokenizer = new StringTokenizer(line, ",");

    // throws away first token
    String field = tokenizer.nextToken();

    if (!tokenizer.hasMoreTokens()) return null;
    String receipent = tokenizer.nextToken();

    if (!tokenizer.hasMoreTokens()) return null;
    String messageCode = tokenizer.nextToken();

    if (!tokenizer.hasMoreTokens()) return null;
    String messageType = tokenizer.nextToken();

    // TODO: to perform validation

    if (messageType.equalsIgnoreCase(FIXED) || messageType.equalsIgnoreCase(VARIABLE)) {

      if (messageType.equalsIgnoreCase(VARIABLE)) {

        if (!tokenizer.hasMoreTokens()) {
          System.out.println(" missing content --> " + line);
          return null;
        }
        String content = line.substring(line.indexOf(VARIABLE+",") + VARIABLE.length()+1);

        return new SMSRecord(-1, lineNo, messageCode, receipent, content, messageCode);

      }
      else {

        if (templateFile != null) {
          String content = templateFile.getValue(messageCode);
          if (content != null) return new SMSRecord(-1, lineNo, messageCode, receipent, content, messageCode);
        }

        System.out.println(" missing content --> " + line);

      }


    }

    return null;
  }
}
