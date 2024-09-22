package seth.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seth.contract.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
