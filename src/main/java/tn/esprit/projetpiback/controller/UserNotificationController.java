package tn.esprit.projetpiback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.projetpiback.entites.Notification;
import tn.esprit.projetpiback.services.impl.ImpUserNotificationService;

import java.util.List;

@RestController
@RequestMapping("/userNotif")
public class UserNotificationController {
    private final ImpUserNotificationService impUserNotificationService;

    public UserNotificationController(ImpUserNotificationService impUserNotificationService) {
        this.impUserNotificationService = impUserNotificationService;
    }

    @GetMapping("/{utilisateurId}/notifications")
    public List<Notification> getNotifications(@PathVariable int IdUser) {
        return impUserNotificationService.getNotifications(IdUser);
    }
}
