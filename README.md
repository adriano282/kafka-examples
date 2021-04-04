# kafka-examples

### How to run 
```cd kafka-examples && docker-compose up -d```

#### Starting the producer
```./gradlew kafka-producer:bootRun```

##### Sending Messages
```
curl -X POST http://localhost:8080/vehicle-coordinate/publish -H 'Content-Type: application/json' --data '{ "x_coordinate" : 10, "y_coordinate" : 15, "vehicle_price" : 15600.50 }'
```

#### Starting the Consumer
```./gradlew kafka-consumer:bootRun```

#### Seeing the messages in Topics:

``` http://localhost:3030/ ```
