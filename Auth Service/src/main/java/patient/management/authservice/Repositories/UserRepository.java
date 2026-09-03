package patient.management.authservice.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import patient.management.authservice.ModelClasses.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
