package com.example.trocd.producer.dto;

import com.example.trocd.producer.enums.CurrencyMoney;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

public record TransactionResponse(

        UUID id,

        String userId,

        BigDecimal amount,

        CurrencyMoney currency,

        String status,

        LocalDateTime createdAt
) {}