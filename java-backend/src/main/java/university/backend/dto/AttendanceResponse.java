package university.backend.dto;
import university.backend.domain.entity.AttendanceEntity;
import java.time.LocalDate;
public record AttendanceResponse(Long id,Long enrollmentId,LocalDate attendanceDate,String status,String note) {
    public static AttendanceResponse from(AttendanceEntity e){
        return new AttendanceResponse(e.getId(),e.getEnrollment().getId(),e.getAttendanceDate(),e.getStatus(),e.getNote());
    }
}