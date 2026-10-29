package com.example.trocd_notification_service.service;

import com.example.trocd_notification_service.enums.NotificationChannel;

public interface NotificationSender {

    NotificationChannel getChannel();

    void send(String recipientId, String messageText);
}
