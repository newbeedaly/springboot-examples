package cn.newbeedaly.route;

import cn.newbeedaly.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recver2 {

  private final static String EXCHANGE_NAME  = "testroute"; // 交换机名称

  public static void main(String[] args) throws Exception{
    Connection connection = ConnectionUtil.getConnect();
    final Channel channel = connection.createChannel();

    channel.queueDeclare("testroute2",false,false,false ,null );
    // 绑定队列到交换机
    // 参数3 标记，绑定到交换机的时候指定标记，只有和他一样的标记才会收到消息
    channel.queueBind("testroute2",EXCHANGE_NAME,"key1");
    // 接收多个标记，再复制一次
    channel.queueBind("testroute2",EXCHANGE_NAME,"key3");
    channel.basicQos(1);
    // 接收消息
    final DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        // 当我们收到消息
        System.out.println("路由消费者2收到的内容是："+new String(body));
        channel.basicAck(envelope.getDeliveryTag(),false);
      }
    };
    channel.basicConsume("testroute2",false,defaultConsumer);
  }
}
