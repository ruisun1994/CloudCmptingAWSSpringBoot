package com.neu.cloudcomputing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"com.neu.cloudcomputing"})
@EnableJpaRepositories(basePackages="com.neu.cloudcomputing.repository")
@EntityScan(basePackages = "com.neu.cloudcomputing.entity")
public class CloudcomputingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudcomputingApplication.class, args);
	}
}
