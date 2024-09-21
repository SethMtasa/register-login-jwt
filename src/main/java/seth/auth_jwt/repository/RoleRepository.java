package seth.auth_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seth.auth_jwt.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
