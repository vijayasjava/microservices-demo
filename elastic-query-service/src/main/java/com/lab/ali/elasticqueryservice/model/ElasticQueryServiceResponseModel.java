package com.lab.ali.elasticqueryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElasticQueryServiceResponseModel
	extends RepresentationModel<ElasticQueryServiceResponseModel> {
    private String id;
    private Long userId;
    private String text;
    private LocalDateTime createdAt;
}