package com.example.trocd.producer.service;

import com.example.trocd.producer.dto.TransactionRequest;
import com.example.trocd.producer.dto.TransactionResponse;
import com.example.trocd.producer.entity.TransactionEntity;
import com.example.trocd.producer.kafka.event.TransactionCreatedEvent;
import com.example.trocd.producer.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public TransactionResponse processTransaction(TransactionRequest request) {

        TransactionEntity entity = new TransactionEntity();
        entity.setUserId(request.userId());
        entity.setAmount(request.amount());
        entity.setCurrency(request.currency());
        entity.setStatus("PENDING");

        TransactionEntity savedEntity = transactionRepository.save(entity);

        TransactionResponse response = new TransactionResponse(
                savedEntity.getId(),
                savedEntity.getUserId(),
                savedEntity.getAmount(),
                savedEntity.getCurrency(),
                savedEntity.getStatus(),
                savedEntity.getCreatedAt());

        applicationEventPublisher.publishEvent(new TransactionCreatedEvent(response));

        return response;
    }
}
