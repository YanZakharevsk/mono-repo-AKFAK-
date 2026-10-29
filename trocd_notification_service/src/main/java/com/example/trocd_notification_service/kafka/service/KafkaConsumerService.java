package com.example.trocd_notification_service.kafka.service;

import com.example.trocd_notification_service.kafka.config.KafkaConfigVars;
import com.example.trocd_notification_service.kafka.dto.TransactionKafkaDto;
import com.example.trocd_notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
    private static final String TRANSACTION_TOPIC = KafkaConfigVars.TRANSACTION_TOPIC;
    private final NotificationService notificationService;

    @KafkaListener(
            topics = TRANSACTION_TOPIC,
            autoStartup = "true",
            containerFactory = "transactionCreatedListenerContainerFactory",
            concurrency = "3"
    )
    public void listenTransaction(
            @Payload TransactionKafkaDto kafkaDto,
            KafkaConsumer<?, ?> consumer,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition
            ){

        log.info("TOPIC: {}. Received message=[{}] from partition {}. Consumer : {}",
                TRANSACTION_TOPIC, kafkaDto, partition, consumer.groupMetadata().memberId());

        notificationService.createNotification(kafkaDto);
    }

}
