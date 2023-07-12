package tn.esprit.projetpiback.dto;
import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileDTO {
    private String newNom;
    private String newPrenom;
    private String newAdresse;
    private Long newTelephone;
    private String newImage;

    // Getters and setters
    // ...
}
