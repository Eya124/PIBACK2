package tn.esprit.projetpiback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.projetpiback.entites.Evenement;
import tn.esprit.projetpiback.entites.Reservation;
import tn.esprit.projetpiback.repository.EvenementRepository;
import tn.esprit.projetpiback.repository.ReservationRepository;
import tn.esprit.projetpiback.services.ReservationService;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final EvenementRepository evenementRepository;
    @Override
    public List<Evenement> getALlbyUserAndDate(Integer idUser) {
        List<Evenement>    evenements = reservationRepository.findDatesEvenementsByUserId(idUser);
        // Filtre les réservations inactives pour chaque événement
        List<Evenement> evenementsFiltres = evenements.stream()
                .peek(evenement -> {
                    List<Reservation> reservationsInactives = evenement.getReservations().stream()
                            .filter(reservation -> !reservation.isActif()&& reservation.getUser().getIdUser().equals(idUser))
                            .collect(Collectors.toList());
                    evenement.setReservations(reservationsInactives);
                })
                .filter(evenement -> !evenement.getReservations().isEmpty())
                .collect(Collectors.toList());

        return evenementsFiltres;
    }

    @Override
    public List<Evenement> getAllEventsArchive(Integer idUser) {
        List<Evenement>    evenements = reservationRepository.findEvenementsByUserId(idUser);
        List<Evenement> evenementsFiltres = evenements.stream()
                .peek(evenement -> {
                    List<Reservation> reservationsActives = evenement.getReservations().stream()
                            .filter(reservation -> reservation.isActif() && reservation.getUser().getIdUser().equals(idUser))
                            .collect(Collectors.toList());
                    evenement.setReservations(reservationsActives);
                })
                .filter(evenement -> !evenement.getReservations().isEmpty())
                .collect(Collectors.toList());

        return evenementsFiltres;
    }



    @Override
  public void insertEvenementWithNbrParticipants(Integer reservationId) {
      Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
      List<Evenement> evenements =  reservation.getEvenements();
      for (Evenement evenement : evenements) {
          Long evenementId  =evenement.getIdEvenement();
          int nbr = reservationRepository.countReservationsByEvenements_IdEvenementAndActif(evenementId , false);
          evenement.setNbrParticipants(nbr);
          evenementRepository.save(evenement);

      }
  }
    @Override
    public int getNbrReservationActifEvenement(Long idEvenement, boolean actif) {
        return reservationRepository.countReservationsByEvenements_IdEvenementAndActif(idEvenement, actif);
    }
    @Override
    public void insertReservationWithActif(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.setActif(true);
        reservationRepository.save(reservation);
        this.insertEvenementWithNbrParticipants(reservationId);
    }
    @Override
    public void supprimerEvenementsExpires() {
        // Logique de suppression des événements expirés après 1 mois
        List<Evenement> evenements =  this.evenementRepository.findAllWithReservations();
        for (int i = 0; i < evenements.size(); i++) {
            Evenement evenement = evenements.get(i);
            Date dateDebut = evenement.getDateDebut();
            LocalDate localDateDebut = dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();
            long daysBetween = ChronoUnit.DAYS.between(localDateDebut, currentDate);
            if (daysBetween >= 30) {
                // Supprimer les réservations associées
                List<Reservation> reservations = evenement.getReservations();
                for (Reservation reservation : reservations) {
                    reservationRepository.delete(reservation);
                }
                // Supprimer la réservation
                evenementRepository.deleteById(evenement.getIdEvenement());
            }
        }
    }
    public List<Evenement> getActiveReservations() {
        List<Evenement> evenements = reservationRepository.findEvenements();
        List<Evenement> evenementsFiltres = evenements.stream()
                .peek(evenement -> {
                    List<Reservation> reservationsActives = evenement.getReservations().stream()
                            .filter(reservation -> reservation.isActif() )
                            .collect(Collectors.toList());
                    evenement.setReservations(reservationsActives);
                })
                .filter(evenement -> !evenement.getReservations().isEmpty())
                .collect(Collectors.toList());
        return evenementsFiltres;
    }
}
