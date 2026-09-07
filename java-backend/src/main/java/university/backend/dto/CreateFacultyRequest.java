package university.backend.dto;
import jakarta.validation.constraints.*;
public record CreateFacultyRequest(@NotBlank @Size(max=160) String name,
                                   @NotBlank @Email @Size(max=220) String email,
                                   @Size(max=20) String departmentCode,@Size(max=120) String specialization) {}