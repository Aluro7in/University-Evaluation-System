package university.backend.dto;
import jakarta.validation.constraints.*;
public record EnrollmentRequest(@NotNull Long studentId,@NotNull Long courseId,@NotBlank String semesterKey) {}