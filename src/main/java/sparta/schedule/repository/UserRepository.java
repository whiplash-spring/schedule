package sparta.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.schedule.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
