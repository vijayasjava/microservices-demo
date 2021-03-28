package com.lab.ali.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.lab.ali.appconfigdata.KafkaConfigData;
import com.lab.ali.appconfigdata.KafkaProducerCofigData;

@Configuration
public class KafkaProducerConfiguration<K extends Serializable,
				V extends SpecificRecordBase> {

	
	private KafkaConfigData kafkaConfigData;
	private KafkaProducerCofigData kafkaProducerCofigData;
	
	public KafkaProducerConfiguration(KafkaConfigData kafkaConfigData,
			KafkaProducerCofigData kafkaProducerCofigData) {
		this.kafkaConfigData = kafkaConfigData;
		this.kafkaProducerCofigData = kafkaProducerCofigData;
	}
	
	@Bean
	public Map<String, Object> producerConfig() {
		
		Map<String, Object> producerConfig = new HashMap<>();
		producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
				this.kafkaConfigData.getBootstrapServers());

		producerConfig.put(this.kafkaConfigData.getSchemaRegistryUrlKey(),
				this.kafkaConfigData.getSchemaRegistryUrl());
		
		producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				this.kafkaProducerCofigData.getKeySerializerClass());
		
		producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				this.kafkaProducerCofigData.getValueSerializerClass());

		producerConfig.put(ProducerConfig.ACKS_CONFIG,
				this.kafkaProducerCofigData.getAcks());
		
		producerConfig.put(ProducerConfig.BATCH_SIZE_CONFIG, 
				this.kafkaProducerCofigData.getBatchSize());
		
		producerConfig.put(ProducerConfig.COMPRESSION_TYPE_CONFIG,
				this.kafkaProducerCofigData.getCompressionType());
		
		producerConfig.put(ProducerConfig.LINGER_MS_CONFIG,
				this.kafkaProducerCofigData.getLingerMs());
		
		producerConfig.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,
				this.kafkaProducerCofigData.getRequestTimeoutMs());
		
		producerConfig.put(ProducerConfig.RETRIES_CONFIG,
				this.kafkaProducerCofigData.getRetryCount());
		
		return producerConfig;
		
	}
	
	@Bean
	public ProducerFactory<K, V> getKafkaProducerFactory() {
		return new DefaultKafkaProducerFactory<>(this.producerConfig());
	}
	
	@Bean
	public KafkaTemplate<K, V> getKafkaTemplate(ProducerFactory<K, V> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}
	
	
}
