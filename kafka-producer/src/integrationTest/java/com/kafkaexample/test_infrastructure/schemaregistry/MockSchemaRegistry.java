package com.kafkaexample.test_infrastructure.schemaregistry;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;

public class MockSchemaRegistry {
    public final static MockSchemaRegistryClient client = new MockSchemaRegistryClient();
}
