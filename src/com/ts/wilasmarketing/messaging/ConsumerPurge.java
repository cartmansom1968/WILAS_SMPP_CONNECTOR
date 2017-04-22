package com.ts.wilasmarketing.messaging;

import com.ts.wilasmarketing.messaging.sms.SMSMessagingObj;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerPurge {
  public static void main (String[] args) throws Exception {
    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory
            ("tcp://localhost:61616");

    Connection connection = factory.createConnection();
    connection.start();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Queue queue = session.createQueue("conn1queue");

    MessageConsumer consumer = session.createConsumer(queue);

    int messages = 0;
    do
    {
      System.out.println("to receive ");
      ObjectMessage message = (ObjectMessage)consumer.receive();
      messages++;
      System.out.println("Message #" + messages);

      SMSMessagingObj smsMessagingObj = (SMSMessagingObj) message.getObject();
      System.out.println(smsMessagingObj);
//      System.out.print(message.getString("mac") + " " + message.getString("venue") + " ");
//      System.out.println(message.getFloat("x") + " " + message.getFloat("y") + " "
//              + message.getInt(BroadcastJMSProducer.FLOOR_NUMBER) + " " + message.getLong(BroadcastJMSProducer.TIMESTAMP));
    } while (true);

//    connection.stop();
//    System.exit(0);
  }

}
