#!/bin/bash
mvn clean package
chmod +x orders-service/target/orders-service-0.0.1-SNAPSHOT.jar
chmod +x number-generate-service/target/number-generate-service-0.0.1-SNAPSHOT.jar
java -jar orders-service/target/orders-service-0.0.1-SNAPSHOT.jar &
java -jar number-generate-service/target/number-generate-service-0.0.1-SNAPSHOT.jar &
