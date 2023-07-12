package tn.esprit.projetpiback.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.projetpiback.entites.Evenement;
import tn.esprit.projetpiback.entites.Reclamation;
import tn.esprit.projetpiback.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,Integer> {
    List<User> findAllByLastLog(LocalDate date);
    List<User> findAllByNomAndPrenom(String nom,String prenom);
    List<User> findAllByFirstlogBetween(LocalDate d1 , LocalDate d2);
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User findUserByUsername(String username);

    List<User> findAllByNbrSignalementIsGreaterThanEqualAndDesactiverIsFalse(int nbrrec);

    List<User> findAllByReclamationsUserarec(User u);

    @Query(value = "SELECT COUNT(*) FROM user WHERE MONTH(firstlog) = MONTH(:date) AND YEAR(firstlog) = YEAR(:date)", nativeQuery = true)
    long getUsersCountThisMonth(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM user WHERE YEAR(firstlog) = YEAR(:date)", nativeQuery = true)
    long getUsersCountThisYear(LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM user WHERE firstlog >= :startOfWeek AND firstlog <= :endOfWeek", nativeQuery = true)
    long getUsersCountThisWeek(LocalDate startOfWeek, LocalDate endOfWeek);



    @Query("SELECT u FROM User u WHERE u.interesse = true")
    List<User> findUsersInteresses();


}
