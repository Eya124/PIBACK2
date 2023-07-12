package tn.esprit.projetpiback.services;

import tn.esprit.projetpiback.entites.Evenement;
import tn.esprit.projetpiback.entites.Reservation;

import java.util.List;

public interface ReservationService {
    List<Evenement> getALlbyUserAndDate(Integer idUser);
    List<Evenement> getAllEventsArchive(Integer idUser);

    //@Scheduled(fixedDelay = 86400000) // Exécute la méthode toutes les 24 heures (86400000 millisecondes)
    void supprimerEvenementsExpires();

    List<Evenement> getActiveReservations();
    void insertEvenementWithNbrParticipants(Integer reservationId);

    int getNbrReservationActifEvenement(Long idEvenement, boolean actif);

    void insertReservationWithActif(Integer reservationId);


}
