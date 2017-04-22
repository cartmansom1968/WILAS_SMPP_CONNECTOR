package com.techstudio.wilasbroadcast;

import com.techstudio.wilasbroadcast.messaging.sms.*;

public class FakeConnector {

  SMSReceiverBox box;
  SMSIntRespSenderBox responder;

  public FakeConnector(String queueName) {

    box = new SMSReceiverBoxImpl(queueName);

    responder = new SMSIntRespSenderBoxImpl(SMSGateways.INTERNAL_RESP_QUEUE);
  }

  void kickoff() {

    while (true) {
      SMSMessagingObj msg = box.receive();

      if (msg != null) {
        System.out.println( " msg received in fake connector " + msg);

        responder.send(" success ", msg);
      }
      else {
        System.out.println(" no message ");
      }
    }
  }

  public static void main(String[] args) {

    FakeConnector connector = new FakeConnector("conn1queue");

    connector.kickoff();

  }

}
