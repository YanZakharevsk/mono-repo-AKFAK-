package com.example.trocd_notification_service.service;

import com.example.trocd_notification_service.enums.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationDispatcher {
    private final Map<NotificationChannel, NotificationSender> senderMap;

    @Autowired
    public NotificationDispatcher(List<NotificationSender> senders) {
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(NotificationSender::getChannel, Function.identity()));

        log.info("Зарегистрировано каналов уведомлений: {}", senderMap.keySet());
    }

    public void dispatch(NotificationChannel channel, String recipientId, String messageText) {
        NotificationSender sender = senderMap.get(channel);

        if (sender == null) {
            log.error("Попытка отправить уведомление через неподдерживаемый канал: {}", channel);
            throw new UnsupportedOperationException("Канал связи не поддерживается: " + channel);
        }

        log.debug("Маршрутизация уведомления через канал {}", channel);
        sender.send(recipientId, messageText);
    }
}
