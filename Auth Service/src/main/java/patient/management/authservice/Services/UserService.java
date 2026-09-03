package patient.management.authservice.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import patient.management.authservice.ModelClasses.User;
import patient.management.authservice.Repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
