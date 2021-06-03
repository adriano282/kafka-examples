package com.kafkaexample.consumer.message;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.listener.adapter.RetryingMessageListenerAdapter;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private static final Logger LOGGER = Logger.getLogger(KafkaConsumerConfig.class.toString());

    private MessageConsumerProps messageConsumerProps;

    @Autowired
    public KafkaConsumerConfig(MessageConsumerProps messageConsumerProps) {
        this.messageConsumerProps = messageConsumerProps;
    }

    @Bean
    public ConsumerFactory consumerFactory() { return new DefaultKafkaConsumerFactory(messageConsumerProps.values()); }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, VehiclePositionCoordinate>>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, VehiclePositionCoordinate> factory =
                new ConcurrentKafkaListenerContainerFactory<Integer, VehiclePositionCoordinate>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean(value = "kafkaListenerContainerFactoryWithDeadLetterPublishingRecoverer")
    @Autowired
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, VehiclePositionCoordinate>>
    kafkaListenerContainerFactoryForDeadLetterTopicOnMainConsumerStrategy(KafkaTemplate kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<Integer, VehiclePositionCoordinate> factory =
                new ConcurrentKafkaListenerContainerFactory<Integer, VehiclePositionCoordinate>();

        factory.setErrorHandler(
                new SeekToCurrentErrorHandler(
                        new DeadLetterPublishingRecoverer(
                                (KafkaOperations<Integer, VehiclePositionCoordinate>) kafkaTemplate), new FixedBackOff(2000L, 2)));
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }

    @Bean(value = "kafkaListenerContainerFactoryForRetryTopic")
    @Autowired
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, VehiclePositionCoordinate>>
    kafkaListenerContainerFactoryForDeadLetterTopicOnSecondConsumerStrategy(KafkaTemplate kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<Integer, VehiclePositionCoordinate> factory =
                new ConcurrentKafkaListenerContainerFactory<Integer, VehiclePositionCoordinate>();

        factory.setConsumerFactory(consumerFactory());
        factory.setRetryTemplate(retryTemplate());
        factory.setRecoveryCallback(
                context -> {

                    ConsumerRecord vehiclePositionCoordinate  = (ConsumerRecord)context.getAttribute(RetryingMessageListenerAdapter.CONTEXT_RECORD);
                    String errorTopic = vehiclePositionCoordinate.topic().replace("_RETRY", "_ERROR");

                    LOGGER.severe(String.format("Routing message %s to %s topic after exhausted attempts.",  (VehiclePositionCoordinate) vehiclePositionCoordinate.value(), errorTopic));

                    kafkaTemplate.send(errorTopic, (VehiclePositionCoordinate) vehiclePositionCoordinate.value());
                    return Optional.empty();
                }
        );
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    public RetryTemplate retryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(10)
                .exponentialBackoff(2000L, 1.1, 40000L)
                .build();
    }
}
