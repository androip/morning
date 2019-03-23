package org.meta_controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"org.meta_controller"})
public class MetaApplication {

	private static Logger logger = LoggerFactory.getLogger(MetaApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(MetaApplication.class, args);
		logger.info("MetaApplication start");
	}
}
