package com.elasticsearch.spring.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * ClassName: ElasticSearchClientConfig
 * Description: TODO
 *
 * @author shengfq
 * @date: 2022/8/29 7:09 下午
 */
@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
public class ElasticSearchClientConfig {

    @Bean
    @ConditionalOnMissingBean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client=new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9201,"http")));
        return client;
    }

}
