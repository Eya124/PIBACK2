package tn.esprit.projetpiback.services;


import tn.esprit.projetpiback.entites.Reclamation;
import tn.esprit.projetpiback.entites.User;

import java.util.List;

public interface ReclamationService {


    void ajouterReclamation(int iduserrec1,int iduserarec2,Reclamation rec);

    void updateRecStatus(int recid,boolean recstatus);
    List<Reclamation> getAllReclamations();

    void supprimerRec();
}
