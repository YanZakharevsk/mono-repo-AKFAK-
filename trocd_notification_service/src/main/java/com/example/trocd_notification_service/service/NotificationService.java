package com.example.trocd_notification_service.service;

import com.example.trocd_notification_service.document.NotificationDocument;
import com.example.trocd_notification_service.enums.NotificationChannel;
import com.example.trocd_notification_service.enums.NotificationStatus;
import com.example.trocd_notification_service.kafka.dto.TransactionKafkaDto;
import com.example.trocd_notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationDispatcher dispatcher;

    @Transactional
    public void createNotification(TransactionKafkaDto transaction) {
        log.info("Формирование уведомления для транзакции: {}", transaction.getId());

        String messageText = String.format("Транзакция %s на сумму %s %s изменена. Текущий статус: %s",
                transaction.getId(), transaction.getAmount(), transaction.getCurrency(), transaction.getStatus());

        NotificationChannel preferredChannel = NotificationChannel.TELEGRAM;

        NotificationDocument document = NotificationDocument.builder()
                .userId(transaction.getUserId().toString())
                .transactionId(transaction.getId().toString())
                .channel(preferredChannel)
                .messageText(messageText)
                .status(NotificationStatus.PENDING)
                .build();

        notificationRepository.save(document);
        log.info("Уведомление [{}] сохранено со статусом PENDING", document.getId());
    }

    public void processPendingNotifications(){
        List<NotificationDocument> pendingList = notificationRepository.findAllByStatus(NotificationStatus.PENDING);

        log.info("Найдено {} уведомлений в статусе PENDING для отправки", pendingList.size());

        if(pendingList.isEmpty()) return;


        for(NotificationDocument doc : pendingList){
            sendAndUpdateStatus(doc);
        }
    }

    private void sendAndUpdateStatus(NotificationDocument doc) {
        try {
            dispatcher.dispatch(doc.getChannel(), doc.getUserId(), doc.getMessageText());

            doc.setStatus(NotificationStatus.SENT);
            doc.setErrorMessage(null);
            log.info("Уведомление [{}] успешно отправлено через {}", doc.getId(), doc.getChannel());

        } catch (Exception e) {
            doc.setStatus(NotificationStatus.FAILED);
            doc.setErrorMessage(e.getMessage());
            log.error("Ошибка отправки уведомления [{}] через {}: {}",
                    doc.getId(), doc.getChannel(), e.getMessage());
        }

        notificationRepository.save(doc);
    }
}
