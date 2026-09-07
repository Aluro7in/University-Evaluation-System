package university.backend.dto;
import jakarta.validation.constraints.*;
import university.backend.domain.enums.StudentType;
public record UpdateStudentRequest(@NotBlank @Size(max=180) String name,@NotNull StudentType type,
                                   @Size(max=160) String major,@NotNull @Min(2000) @Max(2100) Integer enrollmentYear,
                                   @NotNull Boolean active) {}