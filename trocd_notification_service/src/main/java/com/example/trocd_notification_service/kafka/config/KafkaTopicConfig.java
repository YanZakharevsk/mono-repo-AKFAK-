package com.example.trocd_notification_service.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {
    public static final String TRANSACTION_TOPIC = KafkaConfigVars.TRANSACTION_TOPIC;

    private final KafkaConfigVars vars;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, vars.getBootstrapAddress());
        return new KafkaAdmin(configs);
    }

    @Bean
    public Map<String, NewTopic> topics() {
        Map<String, NewTopic> topics = new HashMap<>();
        topics.put(TRANSACTION_TOPIC, transactionTopic());
        return topics;
    }

    @Bean
    public NewTopic transactionTopic() {
        return new NewTopic(TRANSACTION_TOPIC, vars.getNumPartitions(), vars.getReplicationFactor());
    }

}
