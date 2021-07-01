# kafka-examples

This projects demonstrantes how to:
* setup Schema-Registry at producers and consumers;
* setup gradle plugin Java class automatic generation using a plugin;
* setup SASL_SCRAM authentication with KAFKA;
* setup a producer;
* setup a message-by-message consumer;
* setup a batch of messages consumer;
* setup a Dead Letter Topic;
* setup a ERROR processing topoic to free the main topic when occurs errors;
* setup a retry policy to try process again error processing messages;

### How to run 
```cd kafka-examples && docker-compose up -d```

#### Starting the producer
```cd kafka-producer && ./gradlew bootRun```

##### Sending Messages
```
curl -X POST http://localhost:8080/vehicle-coordinate/publish -H 'Content-Type: application/json' --data '{ "x_coordinate" : 10, "y_coordinate" : 15, "vehicle_price" : 15600.50 }'
```

#### Starting the Consumer
```cd kafka-consumer && ./gradlew bootRun```

#### Seeing the messages in Topics:

``` http://localhost:3030/ ```
