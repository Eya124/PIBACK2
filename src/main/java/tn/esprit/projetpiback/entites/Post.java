package tn.esprit.projetpiback.entites;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPost;
    @NotBlank(message = "Le champ link_piecejointe est obligatoire")
    private String linkpiecejointe;
    @NotBlank(message = "Le champ link est obligatoire")
    private String link;
    @NotBlank(message = "Le champ description est obligatoire")
    private String description;
    @NotBlank(message = "Le champ adresse est obligatoire")
    private String adresse;
    @NotBlank(message = "Le champ date est obligatoire")
    private LocalDate date;
    private int nbrParticipant;
    private String typeLogement;
    private String nbrLikes;
   // private int nbr_signalement;

    private Boolean archive;
    @OneToMany(mappedBy = "post")
    private List<Commentaire> commentaires;

    @ManyToOne
    private User userpost;

    @OneToOne
    Feedback feedback;
}
