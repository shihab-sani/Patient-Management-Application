package patient.management.authservice.Services;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import patient.management.authservice.DTOs.LoginRequestDto;
import patient.management.authservice.Util.JwtUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Optional<String> authenticate(LoginRequestDto loginRequestDto){
        return userService
                .getUserByEmail(loginRequestDto.getEmail()).
                filter(user -> passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getEmail(), user.getRole()));
    }

    public boolean validateToken(String token){
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
