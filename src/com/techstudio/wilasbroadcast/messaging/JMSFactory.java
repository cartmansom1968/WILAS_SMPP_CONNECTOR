package com.techstudio.wilasbroadcast.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;

public class JMSFactory {

  ActiveMQConnectionFactory factory;
  Connection connection;
  Session session;
  Queue queue;
  MessageProducer producer;

  private static JMSFactory theInstance;

  static public JMSFactory getInstance() {
    if (theInstance == null) {
      synchronized (JMSFactory.class) {
        if (theInstance == null) {
          theInstance = new JMSFactory();
        }
      }
    }
    return theInstance;
  }

  private JMSFactory() {
    // Create a connection factory referring to the broker host and port
    factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

    try {
      // Note that a new thread is created by createConnection, and it
      //  does not stop even if connection.stop() is called. We must
      //  shut down the JVM using System.exit() to end the program
      connection = factory.createConnection();

      // Start the connection
      connection.start();

    } catch (JMSException e) {
      e.printStackTrace();
    }

  }


  public Session getSession() {
    return session;
  }

  public Session createSession() {
    try {
      // Create a non-transactional session with automatic acknowledgement
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    catch (JMSException e) {
      e.printStackTrace();
    }
    return session;
  }

  public MessageProducer createProducer(String queueName, Session session) {
    try {
      System.out.println("to create queue " + queueName);
      queue = session.createQueue(queueName);

      // Create a producer for test_queue
      return session.createProducer(queue);
    }
    catch (JMSException e) {
      e.printStackTrace();
    }
    return null;
  }

  public MessageProducer createProducer(String queueName) {
    return createProducer(queueName, session);
  }

  public MessageConsumer createConsumer(String queueName, Session session) {
    try {
      System.out.println("to create queue " + queueName);
      queue = session.createQueue(queueName);

      return session.createConsumer(queue);
    }
    catch (JMSException e) {
      e.printStackTrace();
    }
    return null;
  }

  public MessageConsumer createConsumer(String queueName) {
    return createConsumer(queueName, session);
  }




  public void close() {
    // Stop the connection � good practice but redundant here
    try {
      connection.stop();
    } catch (JMSException e) {
      e.printStackTrace();
    }

  }

  static public class JMSConsumer {

    MessageConsumer consumer;
    Session session;


    public JMSConsumer(String name, Session session) {
      this.session = session;
      consumer = JMSFactory.getInstance().createConsumer(name, session);
    }

    public JMSConsumer(String name) {
      session = JMSFactory.getInstance().createSession();

      consumer = JMSFactory.getInstance().createConsumer(name);
    }

    public ObjectMessage receive() {
      // Create a simple text message and send it
//    TextMessage message = session.createTextMessage ("Hello, world!");
      ObjectMessage message = null;
      try {
        return (ObjectMessage) consumer.receive();
      } catch (JMSException e) {
        e.printStackTrace();
      }

      return null;
    }

    public ObjectMessage receiveNoWait() {
      // Create a simple text message and send it
//    TextMessage message = session.createTextMessage ("Hello, world!");
      ObjectMessage message = null;
      try {
        return (ObjectMessage) consumer.receiveNoWait();
      } catch (JMSException e) {
        e.printStackTrace();
      }

      return null;
    }
  }


  static public class JMSProducer {

    MessageProducer producer;
    Session session;


    public JMSProducer(String name) {
      session = JMSFactory.getInstance().createSession();

      producer = JMSFactory.getInstance().createProducer(name);
    }

    public JMSProducer(String name, Session session) {
      this.session = session;
      producer = JMSFactory.getInstance().createProducer(name, session);
    }

    public void send(Serializable messagingObj) {
      // Create a simple text message and send it
//    TextMessage message = session.createTextMessage ("Hello, world!");
      ObjectMessage message = null;
      try {
        message = session.createObjectMessage();

        message.setObject(messagingObj);

        producer.send(message);
      } catch (JMSException e) {
        e.printStackTrace();
      }
    }


  }

  public static void main (String[] args)
          throws Exception  {

    JMSConsumer JMSConsumer = new JMSConsumer("connectorQueue");
    JMSProducer JMSProducer = new JMSProducer("connectorQueue");

    int count = 1;
    while (true) {
//      broadcastJMSProducer.send("mac" + count, "v" + count, 1 + count
//              , 10 + count, 100 + count, 1000 + count   );
      count++;
      Thread.sleep(1000);
    }


//    int count = 1;
//    while (true) {
////      broadcastJMSProducer.send("mac" + count, "v" + count, 1 + count
////              , 10 + count, 100 + count, 1000 + count   );
//      count++;
//      Thread.sleep(1000);
//    }

  }

}
