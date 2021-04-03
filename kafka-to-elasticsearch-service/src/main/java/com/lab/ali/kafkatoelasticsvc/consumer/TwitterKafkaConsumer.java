package com.lab.ali.kafkatoelasticsvc.consumer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.lab.ali.TwitterAvroModel;
import com.lab.ali.appconfigdata.KafkaConfigData;
import com.lab.ali.appconfigdata.KafkaConsumerConfigData;
import com.lab.ali.client.KafkaAdminClient;
import com.lab.ali.elastic.indexclient.service.ElasticIndexClient;
import com.lab.ali.elastic.model.index.TwitterIndexModel;
import com.lab.ali.kafkatoelasticsvc.transformer.AvroToElasticModelTransformer;

import lombok.Data;
import org.springframework.kafka.support.KafkaHeaders;

@Data
@Service
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel> {

	
	
	private static final Logger log =
			LoggerFactory.getLogger(TwitterKafkaConsumer.class);

	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
	
	private KafkaConfigData kafkaConfigData;
	private KafkaConsumerConfigData kafkaConsumerConfigData;
	private KafkaAdminClient kafkaAdminClient;
	private ElasticIndexClient<TwitterIndexModel> elasticIndexClient;
	private AvroToElasticModelTransformer avroToElasticModelTransformer;
	
	public TwitterKafkaConsumer(KafkaConfigData kafkaConfigData,
			KafkaConsumerConfigData kafkaConsumerConfigData,
			KafkaAdminClient kafkaAdminClient,
			KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
			ElasticIndexClient<TwitterIndexModel> elasticIndexClient,
			AvroToElasticModelTransformer avroToElasticModelTransformer) {
		this.kafkaAdminClient = kafkaAdminClient;
		this.kafkaConfigData = kafkaConfigData;
		this.kafkaConsumerConfigData = kafkaConsumerConfigData;
		this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
		this.elasticIndexClient = elasticIndexClient;
		this.avroToElasticModelTransformer = avroToElasticModelTransformer;
	}
	
	@EventListener
	public void onAppStarted(ApplicationStartedEvent applicationStartedEvent) {
		this.kafkaAdminClient.checkSchemaRegistry();
		this.kafkaListenerEndpointRegistry.getListenerContainer("twitterTopicListener").start();
	}
	
      @Override
    	    @KafkaListener(id = "twitterTopicListener", topics = "${kafka-config.topic-name}")
    	    public void consume(@Payload List<TwitterAvroModel> messages,
    	                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Integer> keys,
    	                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
    	                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    	        log.info("{} number of message received with keys {}, partitions {} and offsets {}, " +
    	                        "sending it to elastic: Thread id {}",
    	                messages.size(),
    	                keys.toString(),
    	                partitions.toString(),
    	                offsets.toString(),
    	                Thread.currentThread().getId());
 
      
               messages.stream().forEach(tvm -> {
            	   log.info("message  is : {} ", tvm.getText());
               });
               
               List<TwitterIndexModel> twitterIndexModels = avroToElasticModelTransformer.
            		   getElasticModels(messages);
               List<String> documentIds = elasticIndexClient.save(twitterIndexModels);
               log.info("Documents saved to elasticsearch with ids {}",
            		   documentIds.toArray());

      
      } 
}
