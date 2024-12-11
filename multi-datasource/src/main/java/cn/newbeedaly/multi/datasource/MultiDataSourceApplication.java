package cn.newbeedaly.multi.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//扫描mapper层  并且 取消数据源的自动装配
@MapperScan("cn.newbeedaly.multi.datasource.adaptor")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MultiDataSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDataSourceApplication.class, args);
    }

}
