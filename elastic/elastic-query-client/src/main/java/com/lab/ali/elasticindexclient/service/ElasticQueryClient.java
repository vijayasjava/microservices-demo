package com.lab.ali.elasticindexclient.service;

import java.util.List;
import java.util.Optional;

import com.lab.ali.elastic.model.index.IndexModel;
import com.lab.ali.elastic.model.index.TwitterIndexModel;

public interface ElasticQueryClient<T extends IndexModel> {

	Optional<T> getIndexModelById(String id);
	Optional<List<T>> findIndexModelByText(String text);
	Optional<List<T>> findAllIndexModels();
	
}
