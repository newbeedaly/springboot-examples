package cn.newbeedaly.kafka.kafka.autoconfigure;

import cn.newbeedaly.kafka.kafka.KafkaConfigurationPostProcessor;
import cn.newbeedaly.kafka.kafka.bean.KafkaConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true")
@EnableConfigurationProperties
public class KafkaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.kafka")
    public KafkaConfiguration kafkaConfiguration() {
        return new KafkaConfiguration();
    }

    @Bean
    @ConditionalOnBean(KafkaConfiguration.class)
    @ConditionalOnMissingBean
    public Producer<Object, Object> producer(KafkaConfiguration kafkaConfiguration) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaConfiguration.getProducer().getKeySerializer());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaConfiguration.getProducer().getValueSerializer());
        return new KafkaProducer<>(properties);
    }

    @Bean
    @ConditionalOnBean(KafkaConfiguration.class)
    public KafkaConfigurationPostProcessor initKafkaConfigurationPostProcessor() {
        return new KafkaConfigurationPostProcessor();
    }

}
