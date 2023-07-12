package tn.esprit.projetpiback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetpiback.entites.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String r);
}
