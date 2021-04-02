package com.lab.ali.kafkatoelasticsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
@SpringBootApplication
@ComponentScan(basePackages = "com.lab.ali")
public class KafkaToElasticSearchService {

	public static void main(String[] args) {
		SpringApplication.run(KafkaToElasticSearchService.class);
	}
}
