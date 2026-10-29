package com.example.trocd.loyality_service.kafka.dto;

import com.example.trocd.loyality_service.enums.CurrencyMoney;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionKafkaDto {

    private UUID id;

    private String userId;

    private BigDecimal amount;

    private CurrencyMoney currency;

    private String status;

    private LocalDateTime createdAt;
}
