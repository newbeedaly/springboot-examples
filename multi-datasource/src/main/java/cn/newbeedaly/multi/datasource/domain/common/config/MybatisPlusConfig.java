package cn.newbeedaly.multi.datasource.domain.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * 性能优化  复杂查询可能会报错 建议关掉
     * @return
     */
    /*@Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        *//*SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长*//*
        performanceInterceptor.setMaxTime(1000);
        *//**<SQL是否格式化 默认false*//*
        performanceInterceptor.setFormat(false);
        return performanceInterceptor;
    }*/

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        return new MybatisPlusInterceptor();
    }
}
