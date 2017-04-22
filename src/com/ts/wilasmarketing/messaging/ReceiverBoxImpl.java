package com.ts.wilasmarketing.messaging;

public class ReceiverBoxImpl {
  protected JMSFactory.JMSConsumer consumer;

  public ReceiverBoxImpl(String name) {
    this.consumer = new JMSFactory.JMSConsumer(name);
  }

}
