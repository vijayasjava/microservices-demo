package com.lab.ali.appconfigdata;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "elasticqueryservice")
public class ElasticQueryServiceConfigData {
	private String version;
}
