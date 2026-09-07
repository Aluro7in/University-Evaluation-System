package university.backend.dto;
import university.backend.domain.entity.GradeEntity;
import university.backend.domain.enums.GradeLetter;
import java.time.LocalDateTime;
public record GradeResponse(Long id,Long enrollmentId,String courseCode,String courseName,Integer credits,
                            Integer percentage,Double gpa,GradeLetter letter,Boolean finalized,String instructorNote,
                            LocalDateTime updatedAt) {
    public static GradeResponse from(GradeEntity g){
        return new GradeResponse(g.getId(),g.getEnrollment().getId(),g.getEnrollment().getCourse().getCourseCode(),
                g.getEnrollment().getCourse().getCourseName(),g.getEnrollment().getCourse().getCredits(),
                g.getPercentage(),g.getGpa(),g.getLetter(),g.getFinalized(),g.getInstructorNote(),g.getUpdatedAt());
    }
}