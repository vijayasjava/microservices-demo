package com.lab.ali.elasticqueryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@ComponentScan(basePackages = "com.lab.ali")
//@EnableHypermediaSupport(type = HypermediaType.HAL)
public class ElasticQueryServiceApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ElasticQueryServiceApplication.class, args);
    }
}
