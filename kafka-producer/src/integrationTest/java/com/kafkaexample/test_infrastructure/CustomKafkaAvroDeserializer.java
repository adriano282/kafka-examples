package com.kafkaexample.test_infrastructure;

import com.kafkaexample.test_infrastructure.schemaregistry.MockSchemaRegistry;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

import java.util.Map;

public class CustomKafkaAvroDeserializer extends KafkaAvroDeserializer {

    public CustomKafkaAvroDeserializer() {
        this.schemaRegistry = MockSchemaRegistry.client;
    }

    public CustomKafkaAvroDeserializer(SchemaRegistryClient client) {
        schemaRegistry = MockSchemaRegistry.client;
    }

    public CustomKafkaAvroDeserializer(SchemaRegistryClient client, Map<String, ?> props) {
        schemaRegistry = MockSchemaRegistry.client;
        configure(deserializerConfig(props));
    }
}