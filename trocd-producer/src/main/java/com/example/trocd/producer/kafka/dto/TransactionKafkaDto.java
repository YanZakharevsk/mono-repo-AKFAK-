package com.example.trocd.producer.kafka.dto;

import com.example.trocd.producer.enums.CurrencyMoney;
import com.example.trocd.producer.kafka.event.TransactionCreatedEvent;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionKafkaDto {

    public TransactionKafkaDto(TransactionCreatedEvent event){
        this.id = event.getTransactionResponse().id();
        this.userId = event.getTransactionResponse().userId();
        this.amount = event.getTransactionResponse().amount();
        this.currency  = event.getTransactionResponse().currency();
        this.status = event.getTransactionResponse().status();
        this.createdAt = event.getTransactionResponse().createdAt();
    }
    private UUID id;

    private String userId;

    private BigDecimal amount;

    private CurrencyMoney currency;

    private String status;

    private LocalDateTime createdAt;
}
