package com.techstudio.wilasbroadcast.data;


import com.techstudio.wilasbroadcast.sms.SMSInputParser;

public class MessageFactory {

  SMSInputParser smsInputParser;

  public MessageFactory(SMSInputParser smsInputParser) {
    this.smsInputParser = smsInputParser;
  }

  static private BroadcastRecordType getChannelType(String line) {
    if (line.indexOf(',') > 0) {
      return BroadcastRecordType.getType(line.substring(0, line.indexOf(',')));
    }
    return null;
  }

  BroadcastRecord createMessage(String line, long lineNo) {
    if (line == null) return null;

    BroadcastRecordType channelType = getChannelType(line);

    if (channelType != null) {

      if (channelType == BroadcastRecordType.SMS) {
        return smsInputParser.parse(line, lineNo);
      }

    }

    return null;
  }



}
