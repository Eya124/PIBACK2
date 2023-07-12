package tn.esprit.projetpiback.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.projetpiback.entites.Evenement;
import tn.esprit.projetpiback.entites.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    int countReservationsByEvenements_IdEvenementAndActif (Long idEvenement, boolean actif);
    List<Reservation> findReservationsByUserIdUserAndEvenementsDateDebutBetweenAndActif (Integer idUser, Date dateDebut, Date dateFin, boolean actif);

    @Query("SELECT e FROM Evenement e JOIN e.reservations r WHERE r.user.idUser = :userId and r.actif = false")
    List<Evenement> findDatesEvenementsByUserId(@Param("userId") Integer userId);
    @Query("SELECT e FROM Evenement e JOIN e.reservations r WHERE  r.actif = true")
    List<Evenement> findEvenements();
    @Query("SELECT e FROM Evenement e JOIN e.reservations r WHERE r.user.idUser = :userId and r.actif = true")
    List<Evenement> findEvenementsByUserId(@Param("userId") Integer userId);

    //sl7ha ayed kent hka "findAllByUserIdUserAndEvenementDateDebutBefore"
    List<Reservation> findReservationsByUserIdUserAndEvenementsDateDebutBefore(int iduserrec,Date date);
}
