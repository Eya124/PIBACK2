package tn.esprit.projetpiback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetpiback.dto.AuthResponseDTO;
import tn.esprit.projetpiback.dto.LoginDto;
import tn.esprit.projetpiback.dto.RegisterDto;
import tn.esprit.projetpiback.entites.ResetPasswordToken;
import tn.esprit.projetpiback.entites.Role;
import tn.esprit.projetpiback.entites.User;
import tn.esprit.projetpiback.Exceptions.InvalidTokenException;
import tn.esprit.projetpiback.repository.ResetPasswordTokenRepository;
import tn.esprit.projetpiback.repository.RoleRepository;
import tn.esprit.projetpiback.repository.UsersRepository;
import tn.esprit.projetpiback.security.CustomUserDetailsService;
import tn.esprit.projetpiback.security.JWTGenerator;
import tn.esprit.projetpiback.services.EmailService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final EmailService emailService;
    private final CustomUserDetailsService customUserDetailsService;
    @CrossOrigin
    @Transactional
    @PostMapping("reset-password/request/{email}")
    public ResponseEntity<String> requestPasswordReset(@PathVariable String email) {
        User user = userRepository.findUserByUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Generate a reset password token
        String token = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 24); // Le jeton expirera dans 24 heures
        Date expirationDate = calendar.getTime();
        System.out.println("1234567");

        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(token, expirationDate, user);
        resetPasswordTokenRepository.save(resetPasswordToken);

        // Send email to the user with the password reset link
        String resetPasswordLink = "http://localhost:4200/reset-password/" + token;
        emailService.sendResetPasswordEmail(email, resetPasswordLink);System.out.println("1234567");


        return new ResponseEntity<>("Password reset email sent", HttpStatus.OK);
    }
    @CrossOrigin
    @Transactional
    @PostMapping("admin")
    public User addAdminRoleByEmail(@RequestParam  String email) {
        User user = userRepository.findUserByUsername(email);
        Role adminRole = roleRepository.findByName("ADMIN");
        if (user != null && adminRole != null) {
            List<Role> roles = user.getRoles();
            if (!roles.contains(adminRole)) {
                roles.add(adminRole);
                user.setRoles(roles);
                return userRepository.save(user);
            }
        }
        return null;
    }
@CrossOrigin
    @GetMapping("reset-password/{token}")
    public ResponseEntity<String> validateResetPasswordToken(@PathVariable String token) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);
        if (resetPasswordToken == null || resetPasswordToken.getExpirationDate().before(new Date())) {
            throw new InvalidTokenException("Invalid or expired reset password token");
        }

        return new ResponseEntity<>("Token validated", HttpStatus.OK);
    }
@CrossOrigin
@Transactional
    @PostMapping("reset-password/1/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestParam("newPassword") String newPassword) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);
        if (resetPasswordToken == null || resetPasswordToken.getExpirationDate().before(new Date())) {
            throw new InvalidTokenException("Invalid or expired reset password token");
        }

        User user = resetPasswordToken.getUser();
        System.out.println(user.getPassword());
    System.out.println("newPassword: " + newPassword);
        user.setPassword(passwordEncoder.encode(newPassword));

        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }




   /*@GetMapping("/current")
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userDTO = userRepository.findByUsername(username);
        return userDTO;
    }*/
   @CrossOrigin

  @GetMapping("/current")
    public User getUserConnecte(){
        return userRepository.findUserByUsername(customUserDetailsService.getCurrentUserName())  ;  }
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()));
        User user2 =userRepository.findUserByUsername(loginDto.getUsername());
        user2.setLastLog(LocalDate.now());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
@CrossOrigin
    @PostMapping("register")
public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    if (userRepository.existsByUsername(registerDto.getUsername())) {
        return ResponseEntity.badRequest().body("Username is taken!");
    }

    // Create user object...
    User user = new User();
    user.setUsername(registerDto.getUsername());
    user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
    user.setAge(registerDto.getAge());
    user.setPrenomPere(registerDto.getPrenomPere());
    user.setTelephone(registerDto.getTelephone());
    user.setNom(registerDto.getNom());
    user.setPrenom(registerDto.getPrenom());
    user.setAdresse(registerDto.getAdresse());
    Role roles = roleRepository.findByName(registerDto.getRoleName());
    user.setFirstlog(LocalDate.now());
    user.setRoles(Collections.singletonList(roles));


    userRepository.save(user);

    // Create response object...
    Map<String, String> responseMap = new HashMap<>();
    responseMap.put("message", "User registered successfully!");
    String responseBody = null;
    try {
        responseBody = new ObjectMapper().writeValueAsString(responseMap);
    } catch (JsonProcessingException e) {
        e.printStackTrace();
    }
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>(responseBody, responseHeaders, HttpStatus.OK);
}




}
