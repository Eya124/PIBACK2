package tn.esprit.projetpiback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tn.esprit.projetpiback.entites.Evenement;
import tn.esprit.projetpiback.entites.Reclamation;
import tn.esprit.projetpiback.entites.Reservation;
import tn.esprit.projetpiback.entites.User;
import tn.esprit.projetpiback.repository.EvenementRepository;
import tn.esprit.projetpiback.repository.ReclamationRepository;
import tn.esprit.projetpiback.repository.ReservationRepository;
import tn.esprit.projetpiback.repository.UsersRepository;
import tn.esprit.projetpiback.services.ReclamationService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Calendar;


@Service
@RequiredArgsConstructor
public class ReclamationServiceImp implements ReclamationService {
    private final ReclamationRepository reclamationRepository;
    private final EvenementRepository evenementRepository;
    private final ReservationRepository reservationRepository;
    private final UsersRepository usersRepository;
    @Override
    @Transactional
    public void ajouterReclamation(int iduserrec1,int iduserarec2,Reclamation rec) {
        //check users exists
        User userreclame = usersRepository.findById(iduserrec1).orElse(null);
        User userareclame = usersRepository.findById(iduserarec2).orElse(null);

        Assert.notNull(userareclame,"User doesn't exist");
        Assert.notNull(userreclame,"User doesn't exist");


        //condition  : check whether the user that is going to send a claim is registred in an event
                // get all id evenement of the user
        List<Reservation> reservs = reservationRepository.findReservationsByUserIdUserAndEvenementsDateDebutBefore(iduserrec1,new Date());
        List<Long> idevens=new ArrayList<>();
        for(Reservation x:reservs) {
            for(Evenement e: x.getEvenements()) {
                idevens.add(e.getIdEvenement());
            }
        }
        // condition: check that the one who's bein claimed is the one that posted the even
                // get alll even that has been posted by that user
        List<Evenement> evens2 = evenementRepository.findAllByUsereveIdUser(iduserarec2);
        List<Long> ideven2 = new ArrayList<>();
        for(Evenement evenement:evens2){
            ideven2.add(evenement.getIdEvenement());
        }
    //    System.out.println(idevens);
     //   System.out.println(ideven2);
    //    Assert.notNull(idevens,"No reservation found ");
     //   Assert.notNull(ideven2,"This User didn't post any events");
        // compare the 2 lists
        if (Collections.disjoint(idevens,ideven2)) {
            System.out.println("you cannot send a claim to this person");
        } else {
            //condition 1: check if the user already send a claim to the same user or not
            List<User> usersReclameByUser = usersRepository.findAllByReclamationsUserarec(userareclame);

            if (!usersReclameByUser.isEmpty()) {
                System.out.println("You have already sent a claim to this person");
            }
            else {
                //ajout 1 dans le nombre de reclamation d'un user ||voir fonction ban dans le user service imp
                int addnbrsign = userareclame.getNbrSignalement() + 1;
                userareclame.setNbrSignalement(addnbrsign);
                rec.setUserrec(userreclame);
                rec.setUserarec(userareclame);
                reclamationRepository.save(rec);
            }
        }
    }

    @Override
    @Transactional
    public void updateRecStatus(int recid, boolean recstatus) {
        Reclamation reclamation = reclamationRepository.findById(recid).orElse(null);

        reclamation.setStatus(recstatus);
        reclamation.setDateupdaterec(new Date());
        reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }


    // supprimer les reclamations qui sont traités dépuis 3 jours
    // toutes les 1min 25 secondes pour alléger le travail de serveur
    @Scheduled(fixedDelay = 85000)
    @Transactional
    @Override
    public void supprimerRec() {

        //Calculate the thresholdDate (which is before 3 days from today)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        Date thresholdDate = calendar.getTime();
        System.out.println(thresholdDate);
        //get the recs
        List<Reclamation> recs = reclamationRepository.findAllByStatusIsTrueAndDateupdaterecBefore(thresholdDate);
        for(Reclamation reclamationtraite : recs){
            reclamationRepository.delete(reclamationtraite);
            System.out.println(reclamationtraite.getIdReclamation() +" got deleted");
        }
    }
    //ban user function in userservice




    //zid get by id

}
