package com.lab.ali.elasticindexclient.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lab.ali.appconfigdata.ElasticConfigData;
import com.lab.ali.appconfigdata.ElasticQueryConfigData;
import com.lab.ali.elastic.model.index.TwitterIndexModel;
import com.lab.ali.elasticindexclient.service.ElasticQueryClient;
import com.lab.ali.elasticindexclient.util.ElasticQueryUtil;

import lombok.Data;

@Service
@Data
public class TwitterElasticQueryClient
    implements ElasticQueryClient<TwitterIndexModel>{

	private ElasticConfigData elasticConfigData;
	private ElasticQueryConfigData elasticQueryConfigData;
	private ElasticsearchOperations elasticsearchOperations;
	private ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil;
	
	public TwitterElasticQueryClient(ElasticConfigData elasticConfigData,
			ElasticQueryConfigData elasticQueryConfigData,
			ElasticsearchOperations elasticsearchOperations,
			ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil) {
		
		this.elasticConfigData = elasticConfigData;
		this.elasticQueryConfigData = elasticQueryConfigData;
		this.elasticsearchOperations = elasticsearchOperations;
		this.elasticQueryUtil = elasticQueryUtil;
	}
	
	@Override
	public Optional<TwitterIndexModel> getIndexModelById(String id) {

		return Optional.ofNullable(this.getElasticsearchOperations().
				searchOne(this.getElasticQueryUtil().getSearchQueryById(id),
						TwitterIndexModel.class,
						IndexCoordinates.of(this.getElasticConfigData().getIndexName())))
				.map(t -> t.getContent());
		
	}
	
	@Override
	public Optional<List<TwitterIndexModel>> findIndexModelByText(String text) {

		 return Optional.ofNullable(this.getElasticsearchOperations().
						search(this.getElasticQueryUtil().
						getSearchQueryByField(this.getElasticQueryConfigData().
								getTextField(),
								text),
						TwitterIndexModel.class,
						IndexCoordinates.of(this.elasticConfigData.getIndexName())))
						.map(x -> x.getSearchHits())
						.filter(x -> !CollectionUtils.isEmpty(x))
						.map(x -> x.stream().map(y -> y.getContent()).
								collect(Collectors.toList()));
				 
				 
		
	}
	
	@Override
	public Optional<List<TwitterIndexModel>> findAllIndexModels() {

		return Optional.ofNullable(this.elasticsearchOperations.
										search(this.getElasticQueryUtil().
												getSearchQueryForAll(),
										TwitterIndexModel.class,
										IndexCoordinates.of(this.
												getElasticConfigData().getIndexName())).
										getSearchHits())
		                				.filter(x -> !CollectionUtils.isEmpty(x))
		                				.map(x -> x.stream().map(y -> y.getContent())
		                				.collect(Collectors.toList()));
		
	}
}
