package com.kafkaexample.test_infrastructure.kafka;

import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public abstract class EnabledEmbeddedKafkaServer { 
    
}
