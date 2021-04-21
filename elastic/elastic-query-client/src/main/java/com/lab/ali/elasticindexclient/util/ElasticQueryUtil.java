package com.lab.ali.elasticindexclient.util;

import java.util.Collections;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import com.lab.ali.elastic.model.index.IndexModel;

@Component
public class ElasticQueryUtil<T extends IndexModel> {

	public Query getSearchQueryById(String id) {
		
		return new NativeSearchQueryBuilder().
				withIds(Collections.singletonList(id))
				.build();
	} 

	public Query getSearchQueryByField(String fieldName, String text) {
		
		return new NativeSearchQueryBuilder()
				.withFields(fieldName)
				.withQuery(
						QueryBuilders.boolQuery().
							must(QueryBuilders.matchQuery(fieldName,
						text))).build();
	}
	
	public Query getSearchQueryForAll() {
		
		return new NativeSearchQueryBuilder()
			.withQuery(QueryBuilders.matchAllQuery())
			.build();
	}
}
