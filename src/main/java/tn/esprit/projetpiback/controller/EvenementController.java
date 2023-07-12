package tn.esprit.projetpiback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetpiback.entites.Reservation;
import tn.esprit.projetpiback.services.EvenementService;

@RestController
@RequestMapping("evenement")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EvenementController {
    @Autowired
    private EvenementService iEvenementService;
    @PostMapping("/addAndAssign/{idUser}/{idEvenement}")
    public Reservation addAndAssign( @PathVariable("idUser") Integer idUser , @PathVariable("idEvenement") Long id){
       return iEvenementService.addReservationAndAssignToEvenement(idUser, id);

    }
    }

