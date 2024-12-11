package cn.newbeedaly.work;

import cn.newbeedaly.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recver1 {

  private final static String QUEUE = "testwork"; // 队列名称

  public static void main(String[] args) throws Exception{


    Connection connection = ConnectionUtil.getConnect();
    final Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE,false,false,false,null);
    channel.basicQos(1); // 告诉服务器，没有执行完，不要发新的消息

    // 接收消息
    DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        // 当我们收到消息
        System.out.println("消费者1收到的内容是："+new String(body));

        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        channel.basicAck(envelope.getDeliveryTag(),false); // 参数2 false 为确认收到消息， true 为拒绝收到消息
        //super.handleDelivery(consumerTag, envelope, properties, body);
      }
    };
      // 注册消费者，参数2 为手动确认，我们收到消息后收到告诉服务器我收到消息了
    channel.basicConsume(QUEUE,false,defaultConsumer);
  }
}
