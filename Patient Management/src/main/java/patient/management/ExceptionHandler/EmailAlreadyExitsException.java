package patient.management.ExceptionHandler;

public class EmailAlreadyExitsException extends RuntimeException {
    public EmailAlreadyExitsException(String message) {
        super(message);
    }
}
