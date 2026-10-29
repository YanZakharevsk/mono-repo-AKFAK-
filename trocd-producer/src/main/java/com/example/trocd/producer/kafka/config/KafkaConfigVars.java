package com.example.trocd.producer.kafka.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KafkaConfigVars {
    public static final String TRANSACTION_TOPIC = "transactions.events";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value(value = "${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value(value = "${spring.kafka.admin.properties.partitions}")
    private int numPartitions;

    @Value(value = "${spring.kafka.admin.properties.replication-factor}")
    private short replicationFactor;
}
