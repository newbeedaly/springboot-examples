package cn.newbeedaly.springboot.elasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.List;

/**
 * elasticsearchClient配置类
 *
 * @author newbeedaly
 */
@Slf4j
@Configuration
public class ElasticSearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    public List<String> uris;

    @Value("${spring.elasticsearch.username:}")
    public String username;

    @Value("${spring.elasticsearch.password:}")
    public String password;

    @Value("${spring.elasticsearch.connection-timeout: -1}")
    public int connectionTimeout;

    @Value("${spring.elasticsearch.socket-timeout: -1}")
    public int socketTimeout;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = RestClient.builder(getElasticSearchHttpHosts()).setRequestConfigCallback(requestConfigBuilder -> {
            //设置连接超时时间
            requestConfigBuilder.setConnectTimeout(connectionTimeout);
            requestConfigBuilder.setSocketTimeout(socketTimeout);
            return requestConfigBuilder;
        }).setFailureListener(new RestClient.FailureListener() {
            // 某节点失败,这里可以做一些告警
            @Override
            public void onFailure(Node node) {
                throw new RuntimeException(String.format("connect node failed, node host: %s, node port: %s", node.getHost().getAddress(), node.getHost().getPort()));
            }
        }).setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.disableAuthCaching();
            // 设置账密
            return getHttpAsyncClientBuilder(httpClientBuilder);
        }).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    private HttpHost[] getElasticSearchHttpHosts() {
        HttpHost[] httpHosts = new HttpHost[uris.size()];
        for (int i = 0; i < httpHosts.length; i++) {
            String host = uris.get(i);
            host = host.replaceAll("http://", "").replaceAll("https://", "");
            Assert.isTrue(host.contains(":"), String.format("your host %s format error , Please refer to [ 127.0.0.1:9200 ] ", host));
            httpHosts[i] = new HttpHost(host.split(":")[0], Integer.parseInt(host.split(":")[1]), HttpHost.DEFAULT_SCHEME_NAME);
        }
        return httpHosts;
    }

    private HttpAsyncClientBuilder getHttpAsyncClientBuilder(HttpAsyncClientBuilder httpClientBuilder) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return httpClientBuilder;
        }
        // 账号密码
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        return httpClientBuilder;
    }
}
