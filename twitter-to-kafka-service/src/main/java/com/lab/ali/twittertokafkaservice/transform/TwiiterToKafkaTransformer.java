package com.lab.ali.twittertokafkaservice.transform;

import org.springframework.stereotype.Component;

import com.lab.ali.TwitterAvroModel;

import twitter4j.Status;

@Component
public class TwiiterToKafkaTransformer {

	public TwitterAvroModel transform(Status status) {
		
		return TwitterAvroModel.newBuilder()
			.setCreatedAt(status.getCreatedAt().getTime())
			.setId(status.getId())
			.setText(status.getText())
			.setUserId(status.getUser().getId())
			.build();
		
		
	}
}
