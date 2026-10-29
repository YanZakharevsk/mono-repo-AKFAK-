package com.example.trocd.producer.kafka.service;

import com.example.trocd.producer.kafka.config.KafkaConfigVars;
import com.example.trocd.producer.kafka.dto.TransactionKafkaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    public static final String TRANSACTION_TOPIC = KafkaConfigVars.TRANSACTION_TOPIC;

    private final KafkaTemplate<String, TransactionKafkaDto> kafkaTemplate;

    public void sendTransaction(TransactionKafkaDto kafkaDto){
        kafkaTemplate.send(TRANSACTION_TOPIC, kafkaDto.getId().toString(), kafkaDto).whenComplete((result, throwable) ->
        {
            logKafkaResult(throwable, TRANSACTION_TOPIC, kafkaDto, result);
        });
    }

    private void logKafkaResult(Throwable throwable, String transactionTopic, TransactionKafkaDto kafkaDto, SendResult<String, TransactionKafkaDto> result) {
        if(throwable == null){
            log.info("TOPIC: {}. Sent message=[{}] with offset {} in topic partition {}",
                    transactionTopic,
                    kafkaDto,
                    result.getRecordMetadata().offset(),
                    result.getRecordMetadata().partition());
        }
        else {
            log.error(
                    "TOPIC: {}. Unable to send message=[{}] due to: {}",
                    transactionTopic,
                    kafkaDto,
                    throwable.getMessage(),
                    throwable
            );
        }
    }

}
