package cn.newbeedaly.kafka.kafka.bean;

public class KafkaProducer {

    private String keySerializer;
    private String valueSerializer;

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }
}
