package university.backend.dto;
import university.backend.domain.entity.EnrollmentEntity;
import university.backend.domain.enums.EnrollmentStatus;
import java.time.LocalDateTime;
public record EnrollmentResponse(Long id,Long studentId,String studentName,Long courseId,String courseCode,
                                 String courseName,Integer credits,String semesterKey,EnrollmentStatus status,LocalDateTime enrolledAt) {
    public static EnrollmentResponse from(EnrollmentEntity e){
        return new EnrollmentResponse(e.getId(),e.getStudent().getId(),e.getStudent().getName(),
                e.getCourse().getId(),e.getCourse().getCourseCode(),e.getCourse().getCourseName(),
                e.getCourse().getCredits(),e.getSemesterKey(),e.getStatus(),e.getEnrolledAt());
    }
}