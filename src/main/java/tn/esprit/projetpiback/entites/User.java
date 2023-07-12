package tn.esprit.projetpiback.entites;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Setter
@Getter
@Data
@NoArgsConstructor

@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    private String nom;
    private String prenom;
    private Long telephone;
    private int age;
    private boolean desactiver;
    private Long cin;
    private String prenomPere;
    private int numPere;
    private String password;
    private String username;
    private String adresse;
    private LocalDate firstlog;
    private LocalDate lastLog;
    private String image;
    private int nbrSignalement;
    private boolean interesse;

    @JsonIgnore
    @OneToMany(mappedBy = "userpost")
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "userrec")
    private List<Reclamation> reclamations;


    @OneToMany(mappedBy = "usereve")
    private List<Evenement> evenements;


   @OneToMany
   private List<Notification> notifications;

   // !!!!!!!!! Ayed 31/05/2023 <3 !!!!!!!!!
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "usercommentaire")
    @JsonIgnore
    private List<Commentaire> commentaires;
    public User(String password, String username, List<Role> roles) {
        this.password = password;
        this.username = username;
        this.roles = roles;
    }
}
