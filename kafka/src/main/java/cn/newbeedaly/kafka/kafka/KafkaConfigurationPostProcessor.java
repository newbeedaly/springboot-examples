package cn.newbeedaly.kafka.kafka;

import cn.newbeedaly.kafka.kafka.bean.KafkaConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class KafkaConfigurationPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    public static final Map<TopicPartition, OffsetAndMetadata> CURRENT_OFFSETS = new HashMap<>();

    private ApplicationContext applicationContext;

    /**
     * 自定义bean的后置处理器
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Map<Method, KafkaListener> methodKafkaListenerMap = MethodIntrospector.selectMethods(bean.getClass(),
                (MethodIntrospector.MetadataLookup<KafkaListener>) method ->
                        AnnotatedElementUtils.findMergedAnnotation(method, KafkaListener.class));
        for (Map.Entry<Method, KafkaListener> entry : methodKafkaListenerMap.entrySet()) {
            KafkaConsumer<Object, Object> kafkaConsumer = this.buildKafkaConsumer(entry.getValue());
            KafkaMessageProducerImpl kafkaMessageProducer = new KafkaMessageProducerImpl();

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    while (true) {
                        ConsumerRecords<Object, Object> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(50));
                        if (consumerRecords.iterator().hasNext()) {
                            MethodPostProcessor methodPostProcessor = kafkaMessageProducer.disposeMessage(consumerRecords);
                            MessageAck messageAck = new MessageAckImpl(kafkaConsumer, entry.getValue());
                            methodPostProcessor.invokeMethod(bean, entry.getKey(), messageAck);
                        } else {
                            TimeUnit.SECONDS.sleep(1);
                        }
                    }
                } catch (Exception e) {
                    log.error("拉取kafka消息失败： ", e);
                    e.printStackTrace();
                } finally {
                    kafkaConsumer.close();
                }
            });

        }
        return bean;
    }

    /**
     * 构建kafkaConsumer
     *
     * @param kafkaListener
     * @return
     */
    public KafkaConsumer<Object, Object> buildKafkaConsumer(KafkaListener kafkaListener) {
        Properties properties = new Properties();
        KafkaConfiguration kafkaConfiguration = applicationContext.getBean(KafkaConfiguration.class);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getBootstrapServers());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConfiguration.getConsumer().getKeyDeserializer());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConfiguration.getConsumer().getValueDeserializer());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConfiguration.getConsumer().getAutoOffsetReset());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaListener.group());
        KafkaConsumer<Object, Object> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList(kafkaListener.topics()));
        return kafkaConsumer;
    }

    /**
     * 获取当前上下文，用于替代@Autowired 注解获取参数
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 用于处理方法
     */
    interface MethodPostProcessor {
        /**
         * 调用目标方法
         *
         * @param bean   当前对象
         * @param method 执行的目标方法
         * @param messageAck 提交方式
         * @throws InvocationTargetException
         * @throws IllegalAccessException
         */
        void invokeMethod(Object bean, Method method, MessageAck messageAck) throws InvocationTargetException, IllegalAccessException;
    }

    /**
     * 用于处理监听拉取kafka消息
     */
    interface KafkaMessageProcessor {
        /**
         * 处理请求
         * @param consumerRecords 拉取到的kafka消息
         * @return MethodPostProcessor 返回调用方法接口
         */
        MethodPostProcessor disposeMessage(ConsumerRecords<Object, Object> consumerRecords);
    }

    /**
     * 消息处理
     */
    private static class KafkaMessageProducerImpl implements KafkaMessageProcessor {

        @Override
        public MethodPostProcessor disposeMessage(ConsumerRecords<Object, Object> consumerRecords) {
            return (bean, method, messageAck) -> {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] objects = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (parameterTypes[i].equals(ConsumerRecords.class)) {
                        objects[i] = consumerRecords;
                    }else if (parameterTypes[i].equals(MessageAck.class)){
                        objects[i] = messageAck;
                    }
                }
                method.invoke(bean, objects);
            };
        }
    }

    /**
     * kafka 提交方式
     *  默认自动动提交：自动提交offset
     *  可以配置 KafkaListener 中的 ackMode() MANUAL 配置为手动提交
     */
    private static class MessageAckImpl implements MessageAck {
        private KafkaConsumer<Object, Object> kafkaConsumer = null;
        private KafkaListener kafkaListener = null;

        public MessageAckImpl(KafkaConsumer<Object, Object> kafkaConsumer, KafkaListener kafkaListener) {
            this.kafkaConsumer = kafkaConsumer;
            this.kafkaListener = kafkaListener;
        }

        @Override
        public void ack(long currentOffset,int partition) {
            if (AckMode.AUTOMATIC.equals(kafkaListener.ackMode())) {
                kafkaConsumer.commitAsync();
            } else {
                CURRENT_OFFSETS.put(new TopicPartition(kafkaListener.topics(), partition), new OffsetAndMetadata(currentOffset + 1,""));
                kafkaConsumer.commitAsync(CURRENT_OFFSETS, (offsets, exception) -> {
                    for (Map.Entry<TopicPartition, OffsetAndMetadata> metadataEntry : offsets.entrySet()) {
                        log.info(String.format("手动提交偏移量 topic-partition : %s , OffsetAndMetadata : %s",
                                metadataEntry.getKey(),metadataEntry.getValue()));
                    }
                    if (null != exception){
                        log.info(String.format("手动提交失败： topic : %s , group : %s , partition : %s , offset : %s",
                                kafkaListener.topics(),kafkaListener.group(),partition,currentOffset));
                        exception.printStackTrace();
                    }
                });
            }
        }
    }
}
