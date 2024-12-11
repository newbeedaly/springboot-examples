package cn.newbeedaly.springrabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//连接rabbitMQ的基本配置
@Configuration
@EnableRabbit
public class RabbitConfig {
	 		@Bean
	    public ConnectionFactory connectionFactory() {
	 		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("140.143.26.141");
	 		connectionFactory.setUsername("rabbitmq");
	 		connectionFactory.setPassword("rabbitmq123");
	 		connectionFactory.setPort(5672);
			connectionFactory.setVirtualHost("/test");
	 		return connectionFactory;
	    }

	    @Bean
	    public AmqpAdmin amqpAdmin() {
	        return new RabbitAdmin(connectionFactory());
	    }

	    @Bean
	    public RabbitTemplate rabbitTemplate() {
	        return new RabbitTemplate(connectionFactory());
	    }

	    //配置消费者监听的容器
	    @Bean
	    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
	        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	        factory.setConnectionFactory(connectionFactory());
	        factory.setConcurrentConsumers(3);
	        factory.setMaxConcurrentConsumers(10);
			factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//设置确认模式手工确认
			return factory;
	    }
}
