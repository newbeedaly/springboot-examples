package cn.newbeedaly.kafka.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public interface KafkaTemplate<K, V> {

    RecordMetadata send(String topic, @Nullable K key, V value);

    RecordMetadata send(String topic, Integer partition, @Nullable K key, V value);

    void send(String topic, Integer partition, @Nullable K key, @Nullable V value, Callback callback);

    void send(String topic, @Nullable K key, @Nullable V value, Callback callback);

}
