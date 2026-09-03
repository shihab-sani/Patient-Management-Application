package patient.management.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import patient.management.DTOs.PatientRequestDto;
import patient.management.DTOs.PatientResponseDto;
import patient.management.ExceptionHandler.EmailAlreadyExitsException;
import patient.management.ExceptionHandler.PatientDoesNotExitsException;
import patient.management.GRPC.BillingServiceGrpcClient;
import patient.management.Kafka.KafkaProducer;
import patient.management.Mapper.PatientMapper;
import patient.management.ModelClasses.Patient;
import patient.management.Repositories.PatientRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public List<PatientResponseDto> getPatients() {
        List<Patient>  patients = patientRepository.findAll();
        return patients.stream().map(patientMapper::toDto).toList();
    }

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())){
            throw new EmailAlreadyExitsException("With this Email " + patientRequestDto.getEmail()
                    +" already a patient exists");
        }

        Patient patient = patientMapper.toEntity(patientRequestDto);
        Patient newPatient = patientRepository.save(patient);

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
                newPatient.getName(), newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return patientMapper.toDto(newPatient);
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDto patientRequestDto){
        Patient patient = patientRepository.findById(id).orElseThrow(()->
                new PatientDoesNotExitsException("Patient not found with id: " + id ));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(), id)){
            throw new EmailAlreadyExitsException("With this Email " + patientRequestDto.getEmail()
                    +" already a patient exists");
        }

        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toDto(updatedPatient);
    }

    public void deletePatient(UUID id){
        if(!patientRepository.existsById(id)){
            throw new PatientDoesNotExitsException("Patient not found with id: " + id );
        }
        patientRepository.deleteById(id);
    }
}
