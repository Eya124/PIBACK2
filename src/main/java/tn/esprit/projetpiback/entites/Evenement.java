package tn.esprit.projetpiback.entites;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvenement;
    private String title ;
    private Date dateDebut;
    private Date dateFin;
    private int nbrMaxParticipants;
    private int nbrParticipants;

    @ManyToOne
    private User usereve;
    @ManyToMany
    private List<Reservation> reservations;
}
