package university.backend.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record AttendanceRequest(@NotNull Long enrollmentId,@NotNull LocalDate attendanceDate,
                                @NotBlank String status,@Size(max=400) String note) {}