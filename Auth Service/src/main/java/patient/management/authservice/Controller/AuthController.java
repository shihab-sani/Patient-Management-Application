package patient.management.authservice.Controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import patient.management.authservice.DTOs.LoginRequestDto;
import patient.management.authservice.DTOs.LoginResponseDto;
import patient.management.authservice.Services.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Authenticate user and return JWT token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        Optional<String> tokenAuthentication = authService.authenticate(loginRequestDto);

        if(tokenAuthentication.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenAuthentication.get();
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Operation(summary = "Validate JWT token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
