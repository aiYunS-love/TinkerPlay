package com.baiyx.tinkerplay.Config;

import com.baiyx.tinkerplay.Controller.Elasticsearch.Repository.EsUserRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @Author: baiyx
 * @Date: 2023年9月12日, 0012 下午 3:38:29
 * @Description: Elasticsearch配置类
 */
@Configuration
@EnableJpaRepositories("com.baiyx.tinkerplay.Controller.Elasticsearch.Repository")
public class EsConfig {

    @Bean(name = "myElasticsearchClient")
    public RestHighLevelClient myElasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("192.168.119.128:9200") // 指定Elasticsearch服务器的主机和端口
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean("elasticsearchTemplate")
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        ElasticsearchRestTemplate elasticsearchRestTemplate = new ElasticsearchRestTemplate(myElasticsearchClient());
        return elasticsearchRestTemplate;
    }

    @Bean("myEsUserRepository")
    public EsUserRepository myEsUserRepository() {
        RepositoryFactorySupport factory = new ElasticsearchRepositoryFactory(elasticsearchTemplate());
        return factory.getRepository(EsUserRepository.class);
    }

}
