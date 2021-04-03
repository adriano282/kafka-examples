package com.kafkaexample.producer;

import com.kafkaexample.Main;
import com.kafkaexample.producer.config.KafkaProducerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("unit-testing")
class MainTests {

	@Test
	void contextLoads() {
	}

}
