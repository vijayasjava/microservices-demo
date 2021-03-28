package com.lab.ali.service;

import java.io.Serializable;

import org.apache.avro.specific.SpecificRecordBase;

public interface KafkaProducerService<K extends Serializable,
					V extends SpecificRecordBase> {

	void send(String topicName, K key, V value);
}
