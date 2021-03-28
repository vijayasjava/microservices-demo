package com.lab.ali.twittertokafkaservice.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lab.ali.TwitterAvroModel;
import com.lab.ali.appconfigdata.KafkaConfigData;
import com.lab.ali.appconfigdata.TwitterToKafkaConfiguration;
import com.lab.ali.service.impl.TwitterToKafkaProducerService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import com.lab.ali.twittertokafkaservice.transform.TwiiterToKafkaTransformer;

@Component
@Data
@AllArgsConstructor
public class TwitterStreamListener extends StatusAdapter {

	TwitterToKafkaConfiguration twitterToKafkaConfiguration;
	TwiiterToKafkaTransformer
		TwiiterToKafkaTransformer;
	TwitterToKafkaProducerService twitterToKafkaProducerService;
	KafkaConfigData kafkaConfigData;
	
	private static final Logger log = 
			LoggerFactory.getLogger(TwitterStreamListener.class);

	
	@Override
	public void onStatus(Status status) {
	//	log.info("Tweet received : {}", status);

		TwitterAvroModel twitterAvroModel = 
				this.TwiiterToKafkaTransformer.transform(status);

		this.twitterToKafkaProducerService.send(this.kafkaConfigData.getTopicName(),
				twitterAvroModel.getUserId(),
				twitterAvroModel);
	}
}
