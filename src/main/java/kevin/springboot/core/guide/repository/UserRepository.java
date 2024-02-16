package kevin.springboot.core.guide.repository;

import kevin.springboot.core.guide.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
