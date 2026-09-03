package patient.management.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import patient.management.DTOs.PatientRequestDto;
import patient.management.DTOs.PatientResponseDto;
import patient.management.DTOs.ValidatorClass.CreatePatientValidator;
import patient.management.Services.PatientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patient Management", description = "API endpoints for managing patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "Get all patients", description = "Retrieve a list of all patients in the system")
    public ResponseEntity<List<PatientResponseDto>> getPatients(){
        List<PatientResponseDto> patients = patientService.getPatients();
        return ResponseEntity.ok(patients);
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new patient", description = "Create a new patient in the system")
    public ResponseEntity<PatientResponseDto> createPatient(@Validated({CreatePatientValidator.class, Default.class})
                                                            @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto newPatient = patientService.createPatient(patientRequestDto);
        return ResponseEntity.ok(newPatient);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update an existing patient", description = "Update an existing patient in the system")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id, @Validated({Default.class})
    @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDto updatedPatient = patientService.updatePatient(id, patientRequestDto);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a patient", description = "Delete a patient from the system")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
