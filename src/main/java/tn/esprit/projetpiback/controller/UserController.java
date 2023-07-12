package tn.esprit.projetpiback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetpiback.dto.UpdateProfileDTO;
import tn.esprit.projetpiback.entites.User;
import tn.esprit.projetpiback.repository.UsersRepository;
import tn.esprit.projetpiback.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("user")

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UsersRepository userRepository;
@CrossOrigin
    @GetMapping("/count")
    public Map<String, Long> getUsersCount() {
    return userService.getUsersCount();
    }

    @CrossOrigin
    @GetMapping("/current")
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userDTO = userRepository.findByUsername(username);
        return userDTO;
    }
    @CrossOrigin
    @GetMapping("/{name}/{lastname}")
    public List<User> findPartner(@PathVariable String name, @PathVariable String lastname){
      return userService.findPartner(name,lastname);
    };
    @CrossOrigin
    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAll();
    };
    @CrossOrigin
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }
    @CrossOrigin
    @PostMapping("/update/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody UpdateProfileDTO updateProfileDTO) {
        userService.UpdateUser(id, updateProfileDTO.getNewNom(), updateProfileDTO.getNewPrenom(),
                updateProfileDTO.getNewAdresse(), updateProfileDTO.getNewTelephone(),
                updateProfileDTO.getNewImage());
    }


}
