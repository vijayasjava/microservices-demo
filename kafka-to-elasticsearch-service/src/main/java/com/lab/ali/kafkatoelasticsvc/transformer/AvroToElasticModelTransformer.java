package com.lab.ali.kafkatoelasticsvc.transformer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.lab.ali.TwitterAvroModel;
import com.lab.ali.elastic.model.index.TwitterIndexModel;

@Component
public class AvroToElasticModelTransformer {

    public List<TwitterIndexModel> getElasticModels(List<TwitterAvroModel> avroModels) {
        return avroModels.stream()
                .map((TwitterAvroModel avroModel) -> TwitterIndexModel
                        .builder()
                        .userId(avroModel.getUserId())
                        .id(String.valueOf(avroModel.getId()))
                        .text(avroModel.getText())
                        .createdAt(LocalDateTime.
                        		ofInstant(Instant.
                        				ofEpochMilli(avroModel.getCreatedAt()),
                                ZoneId.systemDefault()))
                        .build()
                ).collect(Collectors.toList());
    }
}
