package com.example.trocd.producer.kafka.event;

import com.example.trocd.producer.kafka.dto.TransactionKafkaDto;
import com.example.trocd.producer.kafka.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class KafkaEventListener {
    private final KafkaProducerService kafkaProducerService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendEventToKafka(TransactionCreatedEvent event){
        TransactionKafkaDto kafkaDto = new TransactionKafkaDto(event);
        kafkaProducerService.sendTransaction(kafkaDto);
    }
}
