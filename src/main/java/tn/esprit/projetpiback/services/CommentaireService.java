package tn.esprit.projetpiback.services;

import tn.esprit.projetpiback.entites.Commentaire;
import tn.esprit.projetpiback.entites.Post;

import java.util.List;

public interface CommentaireService {
    void ajouterCommentaire (Commentaire c, Integer idUser, Integer idPost);
    void filterCommentaire ();
    List<Commentaire> getComentairePost(int idPost);

}
