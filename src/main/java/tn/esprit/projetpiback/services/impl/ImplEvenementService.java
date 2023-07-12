package tn.esprit.projetpiback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tn.esprit.projetpiback.entites.Evenement;
import tn.esprit.projetpiback.entites.Reservation;
import tn.esprit.projetpiback.entites.User;
import tn.esprit.projetpiback.repository.EvenementRepository;
import tn.esprit.projetpiback.repository.ReservationRepository;
import tn.esprit.projetpiback.repository.UsersRepository;
import tn.esprit.projetpiback.services.EvenementService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImplEvenementService implements EvenementService {
    private final EvenementRepository EvenementRepository;
    private final UsersRepository UsersRepository;
    private final ReservationRepository ReservationRepository;
    @Override
    public void insertEvenementWithNbrParticipants(Long idEvenement) {
        Evenement evenement = EvenementRepository.findById(idEvenement).orElseThrow(() -> new IllegalArgumentException("Evenement not found"));
        int nbr = ReservationRepository.countReservationsByEvenements_IdEvenementAndActif(idEvenement, false);
        evenement.setNbrParticipants(nbr);
        EvenementRepository.save(evenement);
    }
    @Override
    @Transactional
    public Reservation addReservationAndAssignToEvenement(Integer idUser, Long idEvenement) {

        Reservation reservation1 = Reservation.builder()
                .build();

        Evenement evenement = EvenementRepository.findById(idEvenement).orElseThrow(() -> new IllegalArgumentException("evenement not found with this id"+idEvenement));
        User user = UsersRepository.findById(idUser).orElseThrow(() -> new IllegalArgumentException("user not found with this id"+idUser));
        Assert.notNull(evenement, "id not found");
        reservation1.setUser(user);
        ReservationRepository.saveAndFlush(reservation1);
        List<Reservation> reservationsActif =ReservationRepository.findReservationsByUserIdUserAndEvenementsDateDebutBetweenAndActif(idUser, evenement.getDateDebut(), evenement.getDateFin(), false);
        Assert.isTrue(reservationsActif.isEmpty(),"Une réservation existe déjà pour cet utilisateur à la même date : " + evenement.getDateDebut());
        Assert.isTrue(evenement.getNbrMaxParticipants() > evenement.getNbrParticipants(),"Désolé, l'événement est complet");
        evenement.getReservations().add(reservation1);//TCHOUF CLASS ELI TJERI
        this.insertEvenementWithNbrParticipants(idEvenement);
        return reservation1;
    }



}
