package patient.management.Mapper;

import org.mapstruct.Mapper;
import patient.management.DTOs.PatientRequestDto;
import patient.management.DTOs.PatientResponseDto;
import patient.management.ModelClasses.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientResponseDto toDto(Patient patient);
    Patient toEntity(PatientRequestDto patientRequestDto);
}
