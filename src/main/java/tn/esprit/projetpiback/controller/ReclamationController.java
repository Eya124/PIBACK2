package tn.esprit.projetpiback.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetpiback.entites.Reclamation;
import tn.esprit.projetpiback.entites.User;
import tn.esprit.projetpiback.services.ReclamationService;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("reclamation")
@RequiredArgsConstructor
public class ReclamationController {
    private final ReclamationService reclamationService;

    @PostMapping("/post/{iduserrec1}/{iduserarec2}")
    public void ajouterReclamation(@PathVariable int iduserrec1,@PathVariable int iduserarec2,@RequestBody Reclamation rec) {
            reclamationService.ajouterReclamation(iduserrec1,iduserarec2,rec);
    }

    @GetMapping("/getall")
    public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }

    @GetMapping("/setStatus/{recid}/{recstatus}")
    public void updateRecStatus(@PathVariable int recid,@PathVariable boolean recstatus) {
        reclamationService.updateRecStatus(recid,recstatus);
    }
}
