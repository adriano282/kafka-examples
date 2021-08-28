package com.kafkaexample.producer.config;

import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@NoArgsConstructor
@Profile(value = {"!test & !integration-test"})
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String BOOTSTRAP_SERVER_CONFIG;

    @Value("${spring.kafka.producer.group-id}")
    private String GROUP_ID_CONFIG;

    @Value("${spring.kafka.producer.key-serializer}")
    private String KEY_SERIALIZER_CLASS_CONFIG;

    @Value("${spring.kafka.producer.value-serializer}")
    private String VALUE_SERIALIZER_CLASS_CONFIG;

    @Value("${spring.kafka.producer.properties.schema.registry.url}")
    private String SCHEMA_REGISTRY_CONFIG;

    @Value("${spring.kafka.producer.linger_ms}")
    private String LINGER_MS_CONFIG;

    @Value("${spring.kafka.producer.acks}")
    private String ACKS_CONFIG;

    @Value("${spring.kafka.producer.retries}")
    private String RETRIES_CONFIG;

    @Value("${spring.kafka.producer.idempotence_enabled}")
    private String ENABLE_IDEMPOTENCE_CONFIG;


    @Bean
    public KafkaTemplate kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderProps());
    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_CONFIG);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KEY_SERIALIZER_CLASS_CONFIG);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS_CONFIG);
        props.put(ProducerConfig.LINGER_MS_CONFIG, LINGER_MS_CONFIG);
        props.put(ProducerConfig.ACKS_CONFIG, ACKS_CONFIG);
        props.put(ProducerConfig.RETRIES_CONFIG, RETRIES_CONFIG);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, ENABLE_IDEMPOTENCE_CONFIG);
        props.put("auto.register.schemas", true);
        props.put("schema.registry.url", SCHEMA_REGISTRY_CONFIG);
        
  
// For authenticating usind SASL SCRAM, set these properties:
// Store and retrieve the password in a secure way
// For the BOOTSTRAP_SERVER, use the protocol 'SASL_SSL://'
//        props.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"adriano\" password=\"adriano-secret\";");
//        props.put("sasl.mechanism", "SCRAM-SHA-512");
//        props.put("security.protocol", "SASL_SSL");

        return props;
    }
}
