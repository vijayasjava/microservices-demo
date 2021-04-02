package com.lab.ali.kafkatoelasticsvc.consumer;

import java.io.Serializable;
import java.util.List;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.lab.ali.TwitterAvroModel;

public interface KafkaConsumer<K extends Serializable,
						V extends SpecificRecordBase> {

	void consume(List<TwitterAvroModel> messages,
			List<Integer> keys,
			List<Integer> partitions,
			List<Long> offsets);
}