package cn.newbeedaly.kafka.kafka.bean;

public class KafkaConfiguration {

    private Boolean enabled;
    private String bootstrapServers;
    private KafkaConsumer consumer = new KafkaConsumer();
    private KafkaProducer producer = new KafkaProducer();

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public KafkaConsumer getConsumer() {
        return consumer;
    }

    public KafkaProducer getProducer() {
        return producer;
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public void setConsumer(KafkaConsumer consumer) {
        this.consumer = consumer;
    }

    public void setProducer(KafkaProducer producer) {
        this.producer = producer;
    }

}
