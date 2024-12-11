package cn.newbeedaly.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 访问地址：http://localhost:10002/swagger-ui.html
 * @SpringBootApplication注解上加上exclude，解除自动加载DataSourceAutoConfiguration
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Swagger2Application {

    public static void main(String[] args) {
        SpringApplication.run(Swagger2Application.class, args);
    }
}
