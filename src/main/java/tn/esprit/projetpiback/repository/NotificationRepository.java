package tn.esprit.projetpiback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetpiback.entites.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    // Ajoutez des méthodes supplémentaires si nécessaire
}
