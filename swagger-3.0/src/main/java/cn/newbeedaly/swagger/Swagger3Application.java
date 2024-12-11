package cn.newbeedaly.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * swagger-3.0.0 <a href="http://localhost:10001/swagger-ui/index.html">访问地址</a>
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Swagger3Application {

    public static void main(String[] args) {
        SpringApplication.run(Swagger3Application.class, args);
    }
}
