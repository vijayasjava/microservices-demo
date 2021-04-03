package com.lab.ali.elastic.indexclient.service;

import java.util.List;

import com.lab.ali.elastic.model.index.IndexModel;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> documents);
}
