package com.lab.ali.elastic.config;

import java.util.Objects;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.lab.ali.appconfigdata.ElasticConfigData;

@Configuration
@EnableElasticsearchRepositories(basePackages = 
{"com.lab.ali.elastic.indexclient.respository",
		"com.lab.ali.elasticindexclient.repository"})
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    private final ElasticConfigData elasticConfigData;

    public ElasticsearchConfig(ElasticConfigData configData) {
        this.elasticConfigData = configData;
    }

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        UriComponents serverUri = UriComponentsBuilder.
        		fromHttpUrl(elasticConfigData.getConnectionUrl()).build();
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(
                        Objects.requireNonNull(serverUri.getHost()),
                        serverUri.getPort(),
                        serverUri.getScheme()
                )).setRequestConfigCallback(
                        requestConfigBuilder ->
                                requestConfigBuilder
                                        .setConnectTimeout(
                                        		elasticConfigData.getConnectionTimeout())
                                        .setSocketTimeout(
                                        		elasticConfigData.getSocketTimeout())

                )
        );
    }
}
