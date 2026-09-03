package patient.management.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import patient.management.DTOs.ValidatorClass.CreatePatientValidator;

@Data
public class PatientRequestDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Date of Birth cannot be blank")
    private String dateOfBirth;

    @NotBlank(groups = CreatePatientValidator.class,message = "Registered Date cannot be blank")
    private String registeredDate;
}
