package panomete.poc.resemail.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import panomete.poc.resemail.security.entity.Auth;

import java.util.UUID;

public interface AuthRepository extends JpaRepository<Auth, UUID> {
    Auth findByUsername(String username);
    Auth findByEmail(String email);
}
