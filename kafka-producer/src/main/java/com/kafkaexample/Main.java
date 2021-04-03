package com.kafkaexample;

import com.kafkaexample.producer.config.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Import(KafkaProducerConfig.class)
@Profile("!unit-testing")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
