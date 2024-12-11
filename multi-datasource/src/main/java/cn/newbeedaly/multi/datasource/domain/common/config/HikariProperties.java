package cn.newbeedaly.multi.datasource.domain.common.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikariProperties {

    // 获取Application.yml中的连接池配置
    @Value("${spring.datasource.hikari.minimumIdle}")
    private int minIdle;

    @Value("${spring.datasource.hikari.maximumPoolSize}")
    private int maxPoolSize;

    @Value("${spring.datasource.hikari.idleTimeout}")
    private int idleTimeout;

    @Value("${spring.datasource.hikari.maxLifetime}")
    private int maxLifetime;

    @Value("${spring.datasource.hikari.connectionTimeout}")
    private int connectionTimeout;

    //数据源初始化
    public HikariDataSource dataSource(HikariDataSource dataSource) {
        //连接超时时间设置
        dataSource.setConnectionTimeout(connectionTimeout);
        //连接空闲生命周期设置
        dataSource.setIdleTimeout(idleTimeout);
        //连接池允许的最大连接数量
        dataSource.setMaximumPoolSize(maxPoolSize);
        //检查空余连接优化连接池设置时间,单位毫秒
        dataSource.setMaxLifetime(maxLifetime);
        //连接池保持最小空余连接数量
        dataSource.setMinimumIdle(minIdle);
        return dataSource;
    }
}
