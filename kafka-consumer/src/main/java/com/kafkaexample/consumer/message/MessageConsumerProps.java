package com.kafkaexample.consumer.message;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageConsumerProps {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String BOOTSTRAP_SERVER_CONFIG;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String KEY_DESERIALIZER_CLASS_CONFIG;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String VALUE_DESERIALIZER_CLASS_CONFIG;

    @Value("${spring.kafka.consumer.properties.schema.registry.url}")
    private String SCHEMA_REGISTRY_CONFIG;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String AUTO_OFFSET_RESET_CONFIG;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String ENABLE_AUTO_COMMIT_CONFIG;

    protected Map<String, Object> values() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KEY_DESERIALIZER_CLASS_CONFIG);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VALUE_DESERIALIZER_CLASS_CONFIG);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, ENABLE_AUTO_COMMIT_CONFIG);
        props.put("specific.avro.reader", true);
        props.put("schema.registry.url", SCHEMA_REGISTRY_CONFIG);
        return props;
    }
}
