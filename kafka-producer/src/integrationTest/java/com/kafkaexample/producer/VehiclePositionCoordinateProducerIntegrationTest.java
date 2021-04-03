package com.kafkaexample.producer;

import avro.vehicle.tracker.VehiclePositionCoordinate;
import com.kafkaexample.Main;
import com.kafkaexample.dto.VehiclePositionCoordinateDTO;
import com.kafkaexample.test_infrastructure.consumer.KafkaConsumerHelper;
import com.kafkaexample.test_infrastructure.kafka.EnabledEmbeddedKafkaServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@ComponentScan(basePackageClasses = {Main.class})
public class VehiclePositionCoordinateProducerIntegrationTest extends EnabledEmbeddedKafkaServer {

    @Autowired
    private KafkaConsumerHelper consumerTestInfrastructure;

    @Autowired
    private VehiclePositionCoordinateProducer producerTargetTest;

    @Value("${topic.name}")
    private String topic;

    @Test
    public void given_aValidMessage_when_trySendToKafka_then_shouldBeAvailableToBeConsumed()
            throws Exception {
        // Arrange
        VehiclePositionCoordinateDTO expected = buildSampleObject();

        // Act
        producerTargetTest.send(expected.toVehiclePositionCoordinate());
        consumerTestInfrastructure.awaitMessageForTenSeconds();

        // Assert
        assertMessageWasReceived();
        assertTheMessageIsEqualsWhatWeSent(expected.toVehiclePositionCoordinate());
    }

    public VehiclePositionCoordinateDTO buildSampleObject() {
        VehiclePositionCoordinateDTO object = new VehiclePositionCoordinateDTO();
        object.setVehicle_price(10.20);
        object.setX_coordinate(10);
        object.setY_coordinate(20);
        object.setVehicle_description("An awesome car!");
        return object;
    }

    public void assertMessageWasReceived() {
        assertTrue(consumerTestInfrastructure.isMessageReceived(), "The message wasn't received as expected.");
    }

    public void assertTheMessageIsEqualsWhatWeSent(VehiclePositionCoordinate expected) {

        VehiclePositionCoordinate result = consumerTestInfrastructure.getReceivedMessage();

        assertThat(expected.getXCoordinate(), equalTo(result.getXCoordinate()));
        assertThat(expected.getYCoordinate(), equalTo(result.getYCoordinate()));
        assertThat(expected.getVehicleDescription(), equalTo(result.getVehicleDescription()));
        assertThat(expected.getVehiclePrice(), equalTo(result.getVehiclePrice()));
    }
}
