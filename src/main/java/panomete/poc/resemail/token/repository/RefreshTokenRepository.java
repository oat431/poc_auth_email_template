package panomete.poc.resemail.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import panomete.poc.resemail.token.entity.RefreshToken;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
}
