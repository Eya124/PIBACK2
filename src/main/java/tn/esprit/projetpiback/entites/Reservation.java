package tn.esprit.projetpiback.entites;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReservation;
    @Column(columnDefinition = "boolean default false")
    private boolean actif ;




    // !!!!!!!!! Ayed 31/05/2023 <3 !!!!!!!!!
    @JsonIgnore
    @ManyToMany(mappedBy = "reservations",cascade = CascadeType.ALL)
    private List<Evenement> evenements;

    @ManyToOne
    private User user;


}
