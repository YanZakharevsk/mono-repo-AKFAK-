package com.example.trocd.loyality_service.kafka.config;

import com.example.trocd.loyality_service.kafka.dto.TransactionKafkaDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final KafkaConfigVars vars;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionKafkaDto>
    transactionCreatedListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, TransactionKafkaDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transactionConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, TransactionKafkaDto> transactionConsumerFactory() {
        JsonDeserializer<TransactionKafkaDto> deserializer = new JsonDeserializer<>(TransactionKafkaDto.class);
        deserializer.addTrustedPackages(vars.getTrustedPackages());
        deserializer.ignoreTypeHeaders();

        return new DefaultKafkaConsumerFactory<>(
                commonConsumerProps(),
                new StringDeserializer(),
                deserializer
        );
    }

    private Map<String, Object> commonConsumerProps() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, vars.getBootstrapAddress(),
                ConsumerConfig.GROUP_ID_CONFIG, vars.getConsumerGroupId(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, vars.getKeyDeserializer(),
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, vars.getValueDeserializer()
        );
    }

}