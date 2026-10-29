package com.example.trocd_notification_service.kafka.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KafkaConfigVars {
    public static final String TRANSACTION_TOPIC = "transactions.events";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value(value = "${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackages;

    @Value(value = "${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value(value = "${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Value(value = "${spring.kafka.admin.properties.partitions}")
    private int numPartitions;

    @Value(value = "${spring.kafka.admin.properties.replication-factor}")
    private short replicationFactor;
}
