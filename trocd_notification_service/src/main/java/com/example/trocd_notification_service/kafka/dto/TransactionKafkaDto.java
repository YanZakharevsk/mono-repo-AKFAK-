package com.example.trocd_notification_service.kafka.dto;

import com.example.trocd_notification_service.enums.CurrencyMoney;
import com.example.trocd_notification_service.enums.NotificationStatus;
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

    private NotificationStatus status;

    private LocalDateTime createdAt;
}
