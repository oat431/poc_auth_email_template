package panomete.poc.resemail.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import panomete.poc.resemail.security.entity.Auth;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<Auth, UUID> {
    Auth findByUsername(String username);
    Auth findByEmail(String email);

    @Query("SELECT a FROM Auth a LEFT JOIN FETCH a.roles WHERE a.username = :username")
    Auth findByUsernameWithRoles(@Param("username") String username);

    @Query("SELECT a FROM Auth a LEFT JOIN FETCH a.roles WHERE a.email = :email")
    Auth findByEmailWithRoles(@Param("email") String email);

}
