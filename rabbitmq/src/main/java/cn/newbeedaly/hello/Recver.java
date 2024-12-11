package cn.newbeedaly.hello;

import cn.newbeedaly.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Recver {
  private final static String QUEUE = "testhello"; // 队列名称

  public static void main(String[] args) throws Exception{
    // 获取连接
    Connection connection = ConnectionUtil.getConnect();
    // 创建通道
    Channel channel = connection.createChannel();
    // 声明队列,参数1 队列名称，2是否持久化队列，3是否排外,4是否自动删除,5其他参数
    channel.queueDeclare(QUEUE,false,false,false,null);
    // 接收消息内容
    QueueingConsumer consumer = new QueueingConsumer(channel); // 定义消费者
    channel.basicConsume(QUEUE,true,consumer);

    while (true){
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      System.out.println(message);
    }

  }
}

