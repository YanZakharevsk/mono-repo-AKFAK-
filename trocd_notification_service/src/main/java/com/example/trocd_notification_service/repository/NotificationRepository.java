package com.example.trocd_notification_service.repository;

import com.example.trocd_notification_service.document.NotificationDocument;
import com.example.trocd_notification_service.enums.NotificationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationDocument, String> {

    List<NotificationDocument> findAllByUserIdOrderByCreatedAtDesc(String userId);

    List<NotificationDocument> findAllByStatus(NotificationStatus status);
}
