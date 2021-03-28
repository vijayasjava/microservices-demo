package com.lab.ali;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.lab.ali.appconfigdata.RetryConfigData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Configuration
@AllArgsConstructor
@Data
public class RetryConfiguration {
	
	private RetryConfigData retryConfigData;
	
	@Bean
	public RetryTemplate getRetryTemplate() {
		
		RetryTemplate retryTemplate = 
				new RetryTemplate();
		
		ExponentialBackOffPolicy backOffPolicy = 
				new ExponentialBackOffPolicy();
		
		backOffPolicy.
			setInitialInterval(this.retryConfigData.getInitialIntervalMs());
		backOffPolicy.
			setMaxInterval(this.retryConfigData.getMaxIntervalMs());
		backOffPolicy.
			setMultiplier(this.retryConfigData.getMultiplier());

		
		retryTemplate.setBackOffPolicy(backOffPolicy);
		
		SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
		simpleRetryPolicy.setMaxAttempts(this.retryConfigData.getMaxAttempts());
		
		retryTemplate.setRetryPolicy(simpleRetryPolicy);
		
		return retryTemplate;
	}

}
