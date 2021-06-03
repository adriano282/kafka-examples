package com.kafkaexample.consumer.batch;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.logging.Logger;

@Configuration
public class KafkaConsumerBachConfig {
    private static final Logger LOGGER = Logger.getLogger(KafkaConsumerBachConfig.class.toString());

    public BatchConsumerProps batchConsumerProps;

    @Autowired
    public KafkaConsumerBachConfig(BatchConsumerProps batchConsumerProps) {
        this.batchConsumerProps = batchConsumerProps;
    }

    @Bean
    public ConsumerFactory batchConsumerFactory() { return new DefaultKafkaConsumerFactory(batchConsumerProps.batchConsumerProps()); }

    @Bean(value = "kafkaListenerContainerFactoryForBatchConsumer")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, VehiclePositionCoordinate>> batchListenerConfig(){
        ConcurrentKafkaListenerContainerFactory<Integer, VehiclePositionCoordinate> factory =
                new ConcurrentKafkaListenerContainerFactory<Integer, VehiclePositionCoordinate>();

        factory.setConsumerFactory(batchConsumerFactory());
        factory.setBatchListener(true);
        return factory;

    }
}
