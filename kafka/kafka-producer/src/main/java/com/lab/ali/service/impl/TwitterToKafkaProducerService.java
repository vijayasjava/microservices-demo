package com.lab.ali.service.impl;

import javax.annotation.PreDestroy;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.lab.ali.TwitterAvroModel;
import com.lab.ali.service.KafkaProducerService;

@Service
public class TwitterToKafkaProducerService implements
      KafkaProducerService<Long, TwitterAvroModel>{

	
	private static final Logger log =
			LoggerFactory.getLogger(TwitterToKafkaProducerService.class);

	
	KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;
	
	public TwitterToKafkaProducerService(KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	@Override
	public void send(String topicName, Long key, TwitterAvroModel value) {
		
		ListenableFuture<SendResult<Long, TwitterAvroModel>>
		   result = this.kafkaTemplate.send(topicName,
				key,
				value);
		
		result.addCallback(new ListenableFutureCallback<SendResult<Long,
				TwitterAvroModel>>() {
			
			public void onSuccess(SendResult<Long, TwitterAvroModel> result) {
				

				log.info("succcess callback : ack with the key: {}", result.getProducerRecord().key());
				log.info("succcess callback : ack with the value: {}", result.getProducerRecord().value());
				
				
				  RecordMetadata metadata = result.getRecordMetadata();
                  log.info("succcess callback : Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                          metadata.topic(),
                          metadata.partition(),
                          metadata.offset(),
                          metadata.timestamp(),
                          System.nanoTime());
			};
			
			@Override
			public void onFailure(Throwable ex) {
				
				log.
				error("failure callback : Error while sending message {} to topic {}", 
						value.toString(), topicName, ex);
			}
		});;
	}
	
	@PreDestroy
	public void preDestroy() {
		if(this.kafkaTemplate != null) {
			this.kafkaTemplate.destroy();
		}
	}
}
