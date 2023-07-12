package tn.esprit.projetpiback.services;

import tn.esprit.projetpiback.entites.Notification;

import java.util.List;

public interface UserNotificationService {
    public List<Notification> getNotifications(int IdUser);
}
