package cn.newbeedaly.route;

import cn.newbeedaly.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Sender {

  private final static String EXCHANGE_NAME  = "testroute"; // 交换机名称

  public static void main(String[] args)  throws Exception{
    Connection connection = ConnectionUtil.getConnect();
    Channel channel =connection.createChannel();
    // 声明交换机
    channel.exchangeDeclare(EXCHANGE_NAME,"direct"); // 定义一个交换机，类型为路由格式
    channel.basicPublish(EXCHANGE_NAME,"key3",null,"路由模式".getBytes());
    channel.close();
    connection.close();
  }
}
