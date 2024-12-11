package cn.newbeedaly.publish;

import cn.newbeedaly.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

  private final static String EXCHANGE_NAME  = "testexchange"; // 交换机名称

  public static void main(String[] args)  throws Exception{
    Connection connection = ConnectionUtil.getConnect();
    Channel channel =connection.createChannel();
    // 声明交换机
    channel.exchangeDeclare(EXCHANGE_NAME,"fanout"); // 定义一个交换机，类型为发布订阅
    channel.basicPublish(EXCHANGE_NAME,"",null,"发布订阅模式".getBytes());
    // 没有消费者，消息会丢失

    channel.close();
    connection.close();
  }
}
