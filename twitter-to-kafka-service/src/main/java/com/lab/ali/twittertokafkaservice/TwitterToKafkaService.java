package com.lab.ali.twittertokafkaservice;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.lab.ali.appconfigdata.TwitterToKafkaConfiguration;
import com.lab.ali.client.KafkaAdminClient;
import com.lab.ali.config.KafkaConfiguration;
import com.lab.ali.twittertokafkaservice.runner.MockKafkaStreamRunner;

@SpringBootApplication
@ComponentScan(basePackages = { "com.lab.ali" })
@Configuration
public class TwitterToKafkaService implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(TwitterToKafkaService.class);

	private TwitterToKafkaConfiguration twitterToKafkaConfiguration;
	private MockKafkaStreamRunner mockKafkaStreamRunner;
	private KafkaInitService kafkaInitService;

	public TwitterToKafkaService(TwitterToKafkaConfiguration twitterToKafkaConfiguration,
			MockKafkaStreamRunner mockKafkaStreamRunner,
			KafkaInitService kafkaInitService) {
		this.twitterToKafkaConfiguration = twitterToKafkaConfiguration;
		this.mockKafkaStreamRunner = mockKafkaStreamRunner;
		this.kafkaInitService = kafkaInitService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaService.class);
	}

	@Override
	public void run(String... args) throws Exception {
		  logger.info("inside run method......");
		  this.twitterToKafkaConfiguration.getFilterkeys().forEach(System.out::println);
		  logger.info(Arrays.toString(this.twitterToKafkaConfiguration.getFilterkeys().toArray()));
		  this.kafkaInitService.init();
		  this.mockKafkaStreamRunner.run();
		  
	}
}
