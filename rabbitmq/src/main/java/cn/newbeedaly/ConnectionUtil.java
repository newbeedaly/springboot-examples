package cn.newbeedaly;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
  public static Connection getConnect() throws Exception {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("140.143.26.141");
    connectionFactory.setPort(5672);
    connectionFactory.setUsername("rabbitmq");
    connectionFactory.setPassword("rabbitmq123");
    connectionFactory.setVirtualHost("/test");
    return connectionFactory.newConnection();
  }
}
