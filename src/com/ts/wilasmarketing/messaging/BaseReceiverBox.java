package com.ts.wilasmarketing.messaging;

public interface BaseReceiverBox<T> {
  T receive();
  T receiveNoWait();
}
