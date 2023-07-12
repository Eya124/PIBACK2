package tn.esprit.projetpiback.services.impl;

import org.springframework.stereotype.Service;
import tn.esprit.projetpiback.entites.Notification;
import tn.esprit.projetpiback.entites.User;
import tn.esprit.projetpiback.repository.UsersRepository;
import tn.esprit.projetpiback.services.UserNotificationService;

import java.util.Collections;
import java.util.List;

@Service
public class ImpUserNotificationService implements UserNotificationService {

    private final UsersRepository usersRepository;

    public ImpUserNotificationService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<Notification> getNotifications(int IdUser) {
        User user = usersRepository.findById(IdUser).orElse(null);
        if (user != null) {
            return user.getNotifications();
        }
        return Collections.emptyList();
    }
}
