package com.lab.ali.elasticqueryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.lab.ali")
public class ElasticQueryServiceApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ElasticQueryServiceApplication.class, args);
    }
}
