package cn.newbeedaly.hello;

import cn.newbeedaly.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 *  hello world 模式
 */
public class Sender {
  private final static String QUEUE = "testhello"; // 队列名称

  public static void main(String[] args) throws Exception{
    // 获取连接
    Connection connection = ConnectionUtil.getConnect();
    // 创建通道
    Channel channel = connection.createChannel();
    // 声明队列,参数1 队列名称，2是否持久化队列，3是否排外,4是否自动删除,5其他参数
    channel.queueDeclare(QUEUE,false,false,false,null);
    // 发送内容
    for (int i= 0; i<100 ; i++) {
      channel.basicPublish("", QUEUE, null,("发送的消息"+i) .getBytes());
    }
    // 关闭连接
    channel.close();
    connection.close();
  }
}
