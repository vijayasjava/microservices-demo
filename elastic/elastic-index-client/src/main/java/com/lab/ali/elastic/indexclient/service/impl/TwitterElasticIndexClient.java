package com.lab.ali.elastic.indexclient.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import com.lab.ali.appconfigdata.ElasticConfigData;
import com.lab.ali.elastic.indexclient.service.ElasticIndexClient;
import com.lab.ali.elastic.indexclient.util.ElasticIndexUtil;
import com.lab.ali.elastic.model.index.TwitterIndexModel;

/*@Service
*//*
 * @ConditionalOnProperty(name = "elastic-config.is-repository", havingValue =
 * "false", matchIfMissing = false)
 */public class TwitterElasticIndexClient implements
	ElasticIndexClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticIndexClient.class);

    private final ElasticConfigData elasticConfigData;

    private final ElasticsearchOperations elasticsearchOperations;

    private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

    public TwitterElasticIndexClient(ElasticConfigData configData,
                                     ElasticsearchOperations elasticOperations,
                                     ElasticIndexUtil<TwitterIndexModel> indexUtil) {
        this.elasticConfigData = configData;
        this.elasticsearchOperations = elasticOperations;
        this.elasticIndexUtil = indexUtil;
    }

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<IndexQuery> indexQueries = elasticIndexUtil.getIndexQueries(documents);
        List<String> documentIds = elasticsearchOperations.bulkIndex(
                indexQueries,
                IndexCoordinates.of(elasticConfigData.getIndexName())
        );
        LOG.info("Documents indexed successfully with type: {} and ids: {}",
        		TwitterIndexModel.class.getName(),
                documentIds);
        return documentIds;
    }
}
