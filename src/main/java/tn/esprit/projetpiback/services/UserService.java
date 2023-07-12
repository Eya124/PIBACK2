package tn.esprit.projetpiback.services;

import tn.esprit.projetpiback.entites.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserService {
    void sendEmail();
    void sendEmailParametre(String to, String subject, String message);
    List<User>  findPartner(String name, String lastname);
    List<User> UserRegistredLastMonth(LocalDate date1 , LocalDate date2);
    public User getMostActiveUser() ;
    public Integer getNewUserWeek();
    public Integer getDifferenceNewWeekLastWeek();
    public List<User> getAll();
    public Map<String, Long> getUsersCount();

    void banUser();
    public User getUser(Integer id);
    public void UpdateUser(Integer id, String newNom, String newPrenom , String newAdresse,Long telephone, String newImage);


}
