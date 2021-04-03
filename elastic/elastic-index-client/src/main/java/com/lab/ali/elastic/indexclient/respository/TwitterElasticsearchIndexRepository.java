package com.lab.ali.elastic.indexclient.respository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.lab.ali.elastic.model.index.TwitterIndexModel;

@Repository
public interface TwitterElasticsearchIndexRepository extends
		ElasticsearchRepository<TwitterIndexModel, Long>{

	
}
