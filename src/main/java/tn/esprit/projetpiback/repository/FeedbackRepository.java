package tn.esprit.projetpiback.repository;

import tn.esprit.projetpiback.entites.Commentaire;
import tn.esprit.projetpiback.entites.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

Feedback findByCommentairefdIdCommentaire(int id);

Feedback findByPostfdIdPost(int id);

}
