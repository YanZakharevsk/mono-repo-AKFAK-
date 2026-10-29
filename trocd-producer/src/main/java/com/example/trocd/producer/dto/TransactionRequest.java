package com.example.trocd.producer.dto;

import com.example.trocd.producer.enums.CurrencyMoney;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransactionRequest(
        @NotBlank(message = "User ID cannot be empty")
        String userId,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotNull(message = "The currency can not be null")
        CurrencyMoney currency
) {}