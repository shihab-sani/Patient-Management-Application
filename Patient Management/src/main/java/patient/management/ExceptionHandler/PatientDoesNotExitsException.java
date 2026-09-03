package patient.management.ExceptionHandler;

public class PatientDoesNotExitsException extends RuntimeException {
    public PatientDoesNotExitsException(String message) {
        super(message);
    }
}
