package com.lab.ali.elasticindexclient.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lab.ali.elastic.model.index.TwitterIndexModel;
import com.lab.ali.elasticindexclient.repository.TwitterElasticsearchQueryRepository;
import com.lab.ali.elasticindexclient.service.ElasticQueryClient;

@Service
@Primary
public class TwitterElasticQueryClientRepositoryImpl
 			implements ElasticQueryClient<TwitterIndexModel>{

	
	private TwitterElasticsearchQueryRepository 
		twitterElasticsearchQueryRepository;
	
	public TwitterElasticQueryClientRepositoryImpl(TwitterElasticsearchQueryRepository 
			twitterElasticsearchQueryRepository) {
		this.twitterElasticsearchQueryRepository = twitterElasticsearchQueryRepository;
	}
	
	@Override
	public Optional<List<TwitterIndexModel>> findAllIndexModels() {
		return Optional.ofNullable(this.twitterElasticsearchQueryRepository.findAll())
			.map(x -> x.iterator())
			.map(x -> { 
							List<TwitterIndexModel> tm = new ArrayList<>();
							x.forEachRemaining(tm::add);
							return tm;           
						 })
			.filter(x -> !CollectionUtils.isEmpty(x));
	}
	
	@Override
	public Optional<List<TwitterIndexModel>> findIndexModelByText(String text) {
		return Optional.ofNullable(this.
				twitterElasticsearchQueryRepository.findByText(text));
	}
	
	@Override
	public Optional<TwitterIndexModel> getIndexModelById(String id) {
		return this.twitterElasticsearchQueryRepository.findById(id);
	}
}
