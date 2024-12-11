package cn.newbeedaly.kafka.kafka;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface KafkaListener {

    @AliasFor("topics")
    String value() default "";

    @AliasFor("value")
    String topics() default "";

    String group() default "";

    AckMode ackMode() default AckMode.AUTOMATIC;

}
