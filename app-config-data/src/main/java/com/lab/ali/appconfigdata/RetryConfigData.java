package com.lab.ali.appconfigdata;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "retry-config")
public class RetryConfigData {
	
	private long initialIntervalMs;
	private int maxIntervalMs;
	private int multiplier;
	private int maxAttempts;
	private long sleepTimeMs;
	
}
