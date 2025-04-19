package panomete.poc.resemail.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import panomete.poc.resemail.role.entity.Role;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String name);
}
