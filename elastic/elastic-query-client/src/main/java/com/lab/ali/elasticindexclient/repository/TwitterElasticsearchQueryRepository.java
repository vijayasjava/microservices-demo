package com.lab.ali.elasticindexclient.repository;

import org.springframework.stereotype.Repository;

import com.lab.ali.elastic.model.index.TwitterIndexModel;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Repository
public interface TwitterElasticsearchQueryRepository extends 
     ElasticsearchRepository<TwitterIndexModel, String>{

	List<TwitterIndexModel> findByText(String text); 
}
