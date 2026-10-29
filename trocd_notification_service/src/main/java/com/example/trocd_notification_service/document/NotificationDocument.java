package com.example.trocd_notification_service.document;

import com.example.trocd_notification_service.enums.NotificationChannel;
import com.example.trocd_notification_service.enums.NotificationStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "notifications")
public class NotificationDocument {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String transactionId;

    private NotificationChannel channel;

    private String messageText;

    @Indexed
    private NotificationStatus status;

    private String errorMessage;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}