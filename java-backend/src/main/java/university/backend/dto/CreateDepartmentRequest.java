package university.backend.dto;
import jakarta.validation.constraints.*;
public record CreateDepartmentRequest(@NotBlank @Size(max=20) String code,@NotBlank @Size(max=160) String name,
                                      @Size(max=200) String school) {}