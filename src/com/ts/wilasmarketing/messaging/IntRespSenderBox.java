package com.ts.wilasmarketing.messaging;

public interface IntRespSenderBox<T> {
  void send(String status, T obj);
}
