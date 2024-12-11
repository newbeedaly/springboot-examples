package cn.newbeedaly.easyexcel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * easyexcel
 * <a href="http://localhost:8003/swagger-ui/index.html">swagger-3.0访问地址</a>
 */
@MapperScan("cn.newbeedaly.easyexcel.*.dao.mapper")
@SpringBootApplication
public class EasyExcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyExcelApplication.class, args);
    }

}
