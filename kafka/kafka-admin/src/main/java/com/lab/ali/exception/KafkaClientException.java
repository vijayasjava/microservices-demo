package com.lab.ali.exception;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class KafkaClientException extends RuntimeException {

	private String messge;
	private Throwable exception;
	
	public KafkaClientException(String messge) {
		super(messge);
		this.messge = messge;
	}
	
	public KafkaClientException(String messge, Throwable exception) {
		super(messge, exception);
		this.messge = messge;
		this.exception = exception;
	}
	
}
