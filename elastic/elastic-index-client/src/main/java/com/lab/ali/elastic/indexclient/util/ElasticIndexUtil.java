package com.lab.ali.elastic.indexclient.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import com.lab.ali.elastic.model.index.IndexModel;

@Component
public class ElasticIndexUtil<T extends IndexModel> {

    public List<IndexQuery> getIndexQueries(List<T> documents) {
        return documents.stream()
                .map(document -> new IndexQueryBuilder()
                        .withId(document.getId())
                        .withObject(document)
                        .build()
                ).collect(Collectors.toList());
    }
}
