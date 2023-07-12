package tn.esprit.projetpiback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.projetpiback.entites.Notification;
import tn.esprit.projetpiback.repository.NotificationRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ImpNotificationService {
    private final NotificationRepository notificationRepository;

    //ajout une notification
    /*public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }*/
    public void createNotification(String content) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setCreatedAt(LocalDate.now());

        // Effectuez des opérations supplémentaires si nécessaire

        notificationRepository.save(notification);
    }
}
