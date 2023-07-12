package tn.esprit.projetpiback.entites;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
}
