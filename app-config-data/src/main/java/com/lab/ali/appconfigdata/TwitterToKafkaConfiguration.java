package com.lab.ali.appconfigdata;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "twittertokafkaservice")
public class TwitterToKafkaConfiguration {
	
	private List<String> filterkeys;
	private String welcomeMessage;
	private String enableMockTweets;
	private int mockMinTweetLength;
	private int mockMaxTweetLength;
	private int mockSleepMs;
	
}
