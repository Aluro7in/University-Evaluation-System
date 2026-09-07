package university.backend.dto;
import jakarta.validation.constraints.*;
public record EvaluationGrade(String courseCode,String courseName,@NotNull @Min(0) @Max(100) Integer percentage,
                               @NotNull @Min(1) @Max(20) Integer credits) {}