package patient.management.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EmailAlreadyExitsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExitsException ex){

        log.warn("Email already exists: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", "Email already exists.");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(PatientDoesNotExitsException.class)
    public ResponseEntity<Map<String, String>> handlePatientDoesNotExitsException(PatientDoesNotExitsException ex){

        log.warn("Patient does not exist: {}", ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("error", "Patient does not exist.");
        return ResponseEntity.badRequest().body(error);
    }
}
