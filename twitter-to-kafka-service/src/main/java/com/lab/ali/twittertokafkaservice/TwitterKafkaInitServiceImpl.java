package com.lab.ali.twittertokafkaservice;

import org.springframework.stereotype.Service;

import com.lab.ali.client.KafkaAdminClient;

@Service
public class TwitterKafkaInitServiceImpl implements KafkaInitService {

	KafkaAdminClient kafkaAdminClient;
	
	public TwitterKafkaInitServiceImpl(KafkaAdminClient kafkaAdminClient) {
		this.kafkaAdminClient = kafkaAdminClient;
	}
	
	@Override
	public void init() {
		this.kafkaAdminClient.createTopics();
		this.kafkaAdminClient.checkSchemaRegistry();
	}
}
