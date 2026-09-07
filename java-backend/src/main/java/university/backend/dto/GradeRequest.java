package university.backend.dto;
import jakarta.validation.constraints.*;
public record GradeRequest(@NotNull Long enrollmentId,@NotNull @Min(0) @Max(100) Integer percentage,
                           @Size(max=500) String instructorNote,Boolean finalize) {}