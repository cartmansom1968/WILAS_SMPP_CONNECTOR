package com.ts.wilasmarketing.messaging;

public interface BaseSenderBox<T> {
  void send(T message);
}
