package com.example.trocd_notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 10000)
    public void runPendingNotificationJob(){
        log.debug("Запуск джоба по обработке PENDING уведомлений...");
        notificationService.processPendingNotifications();
    }
}
