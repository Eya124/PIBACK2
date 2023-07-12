package tn.esprit.projetpiback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetpiback.entites.Commentaire;
import tn.esprit.projetpiback.services.CommentaireService;
import tn.esprit.projetpiback.services.PostService;

import java.util.List;

@RestController
@Validated
@RequestMapping("commentaire")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200") // Remplacez l'origine par l'URL de votre frontend Angular
public class CommentaireController {
    private final CommentaireService commentaireService;
    @PostMapping("/addCommentaire/{idUser}/{idPost}")
    public void ajouterCommentaire(@RequestBody Commentaire c, @PathVariable Integer idUser,@PathVariable Integer idPost) {
        commentaireService.ajouterCommentaire(c,idUser,idPost);
    }
    @GetMapping("/getCommentaires/{idPost}")
    public List<Commentaire> getComentairePost(@PathVariable int idPost){
        return commentaireService.getComentairePost(idPost);
    }
}
