package cn.newbeedaly.kafka.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Service
public class KafkaTemplateImpl<K, V> implements KafkaTemplate<K, V> {

    @Autowired(required = false)
    private Producer<Object, Object> producer;
    /**
     * 发送同步消息
     * @param topic
     * @param key
     * @param value
     * @return
     */
    @Override
    public RecordMetadata send(String topic, Object key, Object value) {
        try {
            ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>(topic, key, value);
            Future<RecordMetadata> send = producer.send(producerRecord);
            return send.get();
        }catch (Exception e){
            log.error("发送指定分区异步消息-报错异常：", e);
            e.printStackTrace();
        }finally {
            producer.flush();
        }
        return null;
    }

    /**
     * 指定分区发送同步消息
     * @param topic
     * @param partition
     * @param key
     * @param value
     * @return
     */
    @Override
    public RecordMetadata send(String topic, Integer partition, Object key, Object value) {
        try {
            ProducerRecord<Object, Object> producerRecord = new ProducerRecord<Object, Object>(topic, partition, key, value);
            Future<RecordMetadata> future = producer.send(producerRecord);
            return future.get();
        }catch (Exception e){
            log.error("发送指定分区异步消息-报错异常：", e);
            e.printStackTrace();
        }finally {
            producer.flush();
        }
        return null;
    }

    @Override
    public void send(String topic, Integer partition, Object key, Object value, Callback callback) {
        try {
            ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>(topic, partition, key, value);
            producer.send(producerRecord,callback);
        }catch (Exception e){
            log.error("发送指定分区异步消息-报错异常：", e);
            e.printStackTrace();
        }finally {
            producer.flush();
        }
    }

    /**
     * 异步发送kafka消息
     * @param topic
     * @param key
     * @param value
     * @param callback
     */
    @Override
    public void send(String topic, Object key, Object value, Callback callback) {
        try {
            ProducerRecord<Object, Object> producerRecord = new ProducerRecord<Object, Object>(topic, key, value);
            producer.send(producerRecord,callback);
        }catch (Exception e){
            log.error("发送异步消息-报错异常：", e);
            e.printStackTrace();
        }finally {
            producer.flush();
        }
    }
}
