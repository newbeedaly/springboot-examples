package cn.newbeedaly.kafka.kafka.bean;

public class KafkaConsumer {
    private String keyDeserializer;
    private String valueDeserializer;
    private String autoOffsetReset;

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public String getKeyDeserializer() {
        return keyDeserializer;
    }

    public String getValueDeserializer() {
        return valueDeserializer;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    public void setKeyDeserializer(String keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
    }

    public void setValueDeserializer(String valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }
}
