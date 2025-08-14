package sparta.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.schedule.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
