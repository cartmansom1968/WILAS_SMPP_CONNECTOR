package com.ts.wilasmarketing.messaging;

public class SenderBoxImpl {
  protected JMSFactory.JMSProducer producer;

  public SenderBoxImpl(String name) {
    this.producer = new JMSFactory.JMSProducer(name);
  }
}
