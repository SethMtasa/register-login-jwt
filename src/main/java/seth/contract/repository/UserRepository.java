package seth.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seth.contract.model.User;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndActiveStatus(String username, boolean activeStatus);
    List<User> findByActiveStatus(boolean activeStatus);
}
