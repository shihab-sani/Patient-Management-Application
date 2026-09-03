package patient.management.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import patient.management.ModelClasses.Patient;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
}
