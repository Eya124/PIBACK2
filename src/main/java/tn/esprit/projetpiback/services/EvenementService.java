package tn.esprit.projetpiback.services;

import tn.esprit.projetpiback.entites.Reservation;

public interface EvenementService {
    void insertEvenementWithNbrParticipants(Long idEvenement);

    Reservation addReservationAndAssignToEvenement(Integer idUser, Long idEvenement);
}
