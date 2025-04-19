package panomete.poc.resemail.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import panomete.poc.resemail.user.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
