package university.backend.dto;
import jakarta.validation.constraints.*;
import java.util.List;
public record CreateCourseRequest(@NotBlank @Size(max=32) String courseCode,@NotBlank @Size(max=200) String courseName,
                                   @NotNull @Min(1) @Max(12) Integer credits,@Size(max=600) String description,
                                   @Size(max=120) String department,@NotNull @Min(1) @Max(500) Integer capacity,
                                   List<String> prerequisiteCodes) {}