package cn.newbeedaly.kafka.kafka;

public interface MessageAck {

    void ack(long currentOffset,int partition);

}
