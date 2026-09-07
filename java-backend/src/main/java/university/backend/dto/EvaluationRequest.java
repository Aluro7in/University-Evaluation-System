package university.backend.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
public record EvaluationRequest(@NotBlank String studentType,@NotEmpty List<@Valid EvaluationGrade> grades) {}