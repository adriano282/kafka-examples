package com.kafkaexample.test_infrastructure;

import com.kafkaexample.test_infrastructure.schemaregistry.MockSchemaRegistry;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import java.util.Map;

public class CustomKafkaAvroSerializer extends KafkaAvroSerializer {
    public CustomKafkaAvroSerializer() {
        this.schemaRegistry = MockSchemaRegistry.client;
    }

    public CustomKafkaAvroSerializer(SchemaRegistryClient client) {
        schemaRegistry = MockSchemaRegistry.client;
    }

    public CustomKafkaAvroSerializer(SchemaRegistryClient client, Map<String, ?> props) {
        schemaRegistry = MockSchemaRegistry.client;
        configure(serializerConfig(props));
    }
}