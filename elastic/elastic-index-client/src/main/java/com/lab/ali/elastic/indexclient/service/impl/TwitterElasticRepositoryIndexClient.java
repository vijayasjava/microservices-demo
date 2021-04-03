package com.lab.ali.elastic.indexclient.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.lab.ali.elastic.indexclient.respository.TwitterElasticsearchIndexRepository;
import com.lab.ali.elastic.indexclient.service.ElasticIndexClient;
import com.lab.ali.elastic.model.index.TwitterIndexModel;

@Service
@ConditionalOnProperty(name = "elastic-config.is-repository",
	havingValue = "true", matchIfMissing = true)
public class TwitterElasticRepositoryIndexClient 
		implements ElasticIndexClient<TwitterIndexModel> {


    private static final Logger LOG = 
    		LoggerFactory.getLogger(TwitterElasticRepositoryIndexClient.class);

    private final TwitterElasticsearchIndexRepository 
    	twitterElasticsearchIndexRepository;

    public TwitterElasticRepositoryIndexClient(
    		TwitterElasticsearchIndexRepository indexRepository) {
        this.twitterElasticsearchIndexRepository = indexRepository;
    }

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<TwitterIndexModel> repositoryResponse =
                (List<TwitterIndexModel>) twitterElasticsearchIndexRepository.
                saveAll(documents);
        List<String> ids = repositoryResponse.stream().
        		map(TwitterIndexModel::getId).collect(Collectors.toList());
        LOG.info("Documents indexed successfully with type: {} and ids: {}",
        		TwitterIndexModel.class.getName(), ids);
        return ids;
    }


}
