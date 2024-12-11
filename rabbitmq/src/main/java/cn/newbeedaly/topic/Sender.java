package cn.newbeedaly.topic;

import cn.newbeedaly.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

  private final static String EXCHANGE_NAME  = "testtopic"; // 交换机名称

  public static void main(String[] args)  throws Exception{
    Connection connection = ConnectionUtil.getConnect();
    Channel channel =connection.createChannel();
    // 声明交换机
    channel.exchangeDeclare(EXCHANGE_NAME,"topic"); // 定义一个交换机
    channel.basicPublish(EXCHANGE_NAME,"key.1.2",null,"topic 模式".getBytes());
    channel.close();
    connection.close();
  }
}
