package tn.esprit.projetpiback.entites;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.projetpiback.services.FeedbackService;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCommentaire;
    private String content;

    private LocalDate createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "post")
    //@JsonBackReference
    @JsonIgnore
    private Post post;
//    @ManyToOne
//    @JsonBackReference
//    private Commentaire parentComment;
//    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Commentaire> replies;
    private Boolean archive;

    @ManyToOne
    private User usercommentaire;
    @OneToOne
    private Feedback feedback;
}