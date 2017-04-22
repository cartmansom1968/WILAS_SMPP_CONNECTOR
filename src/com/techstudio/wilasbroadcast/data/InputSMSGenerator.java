package com.techstudio.wilasbroadcast.data;


import com.techstudio.wilasbroadcast.sms.SMSRecord;

public class InputSMSGenerator implements  InputSource {

  private final long startId;
  private final long numberToSend;
  private final String tpoa;
  private final String messageCode;

  private long counter = 0;

  public InputSMSGenerator(long startId, String tpoa, long numberToSend, String messageCode) {
    this.startId = startId;
    this.tpoa = tpoa;
    this.numberToSend = numberToSend;
    this.messageCode = messageCode;
  }

  @Override
  public BroadcastRecord getNextMessage() {
    if (counter >= numberToSend) return null;
    long id = startId + counter++;
    return new SMSRecord(id, id, tpoa, id +"", "content " + id, messageCode);
  }

}
