package cn.newbeedaly.publish;

import cn.newbeedaly.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recver2 {

  private final static String EXCHANGE_NAME  = "testexchange"; // 交换机名称

  public static void main(String[] args) throws Exception{
    Connection connection = ConnectionUtil.getConnect();
    final Channel channel = connection.createChannel();

    channel.queueDeclare("testpubqueque2",false,false,false ,null );
    // 绑定队列到交换机
    channel.queueBind("testpubqueque2",EXCHANGE_NAME,"");
    channel.basicQos(1);
    // 接收消息
    final DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        // 当我们收到消息
        System.out.println("订阅消费者2收到的内容是："+new String(body));
        channel.basicAck(envelope.getDeliveryTag(),false);
      }
    };
    channel.basicConsume("testpubqueque2",false,defaultConsumer);
  }
}
