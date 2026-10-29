package com.example.trocd.producer.kafka.event;

import com.example.trocd.producer.dto.TransactionResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransactionCreatedEvent {
    private final TransactionResponse transactionResponse;
}
