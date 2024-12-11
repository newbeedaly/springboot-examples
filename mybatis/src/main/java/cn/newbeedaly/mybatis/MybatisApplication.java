package cn.newbeedaly.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.newbeedaly.mybatis.mapper") //扫描的mapper
@EnableTransactionManagement // 开启事务
//@ComponentScan(basePackages = { "cn.newbeedaly.mybatis.controller" , "cn.newbeedaly.mybatis.service", "cn.newbeedaly.mybatis.mapper"}) // 组件扫描
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

}
