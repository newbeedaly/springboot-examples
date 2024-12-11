package cn.newbeedaly.kafka.consumer;


import cn.newbeedaly.kafka.kafka.AckMode;
import cn.newbeedaly.kafka.kafka.KafkaListener;
import cn.newbeedaly.kafka.kafka.MessageAck;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = "test", group = "test", ackMode = AckMode.MANUAL)
    public void testListener(ConsumerRecords<String, String> consumerRecords, MessageAck messageAck) {
        consumerRecords.forEach(consumerRecord -> {
            String key = consumerRecord.key();
            String value = consumerRecord.value();
            System.out.println("key = " + key + "; value = " + value);
            // 手动提交
            messageAck.ack(consumerRecord.offset(), consumerRecord.partition());
        });
    }

    @KafkaListener(topics = "test", group = "test2", ackMode = AckMode.AUTOMATIC)
    public void testListener2(ConsumerRecords<String, String> consumerRecords) {
        consumerRecords.forEach(consumerRecord -> {
            String key = consumerRecord.key();
            String value = consumerRecord.value();
            System.out.println("key = " + key + "; value = " + value);
        });
    }

}
