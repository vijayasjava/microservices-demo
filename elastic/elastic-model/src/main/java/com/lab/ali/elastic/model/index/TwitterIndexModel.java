package com.lab.ali.elastic.model.index;

import java.time.LocalDateTime;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(indexName = "${elastic-config.indexName}")
public class TwitterIndexModel implements IndexModel {

	@JsonProperty
	private String id;
	
	@JsonProperty
    private Long userId;
	
	@JsonProperty
    private String text;
	
    @Field(type = FieldType.Date, 
    		format = DateFormat.custom,
    		pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
	@JsonFormat(shape = Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
	@JsonProperty
    private LocalDateTime createdAt;

	
}
