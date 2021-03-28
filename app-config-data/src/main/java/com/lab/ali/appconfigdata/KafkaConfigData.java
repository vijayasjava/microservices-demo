package com.lab.ali.appconfigdata;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "kafka-config")
@Data
public class KafkaConfigData {

	private String bootstrapServers;
	private String schemaRegistryUrlKey;
	private String schemaRegistryUrl;
	private String topicName;
	private List<String> topicNamesToCreate;
	private Integer numOfPartitions;
	private Short replicationFactor;
	
}
