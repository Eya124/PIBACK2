package tn.esprit.projetpiback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.projetpiback.entites.Evenement;
import java.util.Date;
import java.util.List;
public interface EvenementRepository extends JpaRepository<Evenement,Long> {

    @Query("SELECT DISTINCT e FROM Evenement e LEFT JOIN FETCH e.reservations")
    List<Evenement> findAllWithReservations();


// sl7ha ayed kent hka "findAllUserIdUser"
    //findByUsereveIdUser
    List<Evenement> findAllByUsereveIdUser (int iduser);

}
