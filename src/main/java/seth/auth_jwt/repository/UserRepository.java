package seth.auth_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seth.auth_jwt.model.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
