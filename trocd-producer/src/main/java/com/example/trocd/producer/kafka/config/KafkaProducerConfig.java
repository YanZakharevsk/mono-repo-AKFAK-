package com.example.trocd.producer.kafka.config;

import com.example.trocd.producer.kafka.dto.TransactionKafkaDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final KafkaConfigVars vars;

    @Bean
    public KafkaTemplate<String, TransactionKafkaDto> transactionKafkaTemplate(){
        return new KafkaTemplate<>(transactionProducerFactory());
    }

    @Bean
    public ProducerFactory<String, TransactionKafkaDto> transactionProducerFactory(){
        Map<String, Object> configProps= Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, vars.getBootstrapAddress(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, vars.getKeySerializer(),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, vars.getValueSerializer()
        );

        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
