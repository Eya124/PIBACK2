package tn.esprit.projetpiback.entites;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFeedback;

    private int feedbackNumber;

    @OneToOne
    private Post postfd;

    @OneToOne
    private Commentaire commentairefd;

}
