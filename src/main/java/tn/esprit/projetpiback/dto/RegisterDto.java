package tn.esprit.projetpiback.dto;


import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String nom;
    private String prenom;
    private Long telephone;
    private int age;
    private String username;
    private String password;
    private String prenomPere;
    private String roleName;
    private String adresse;
    private String image;



}

