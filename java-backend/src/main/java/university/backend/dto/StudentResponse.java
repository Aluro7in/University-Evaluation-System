package university.backend.dto;
import university.backend.domain.entity.StudentEntity;
import university.backend.domain.enums.StudentType;
public record StudentResponse(Long id,String studentId,String name,StudentType type,String major,Integer enrollmentYear,Boolean active) {
    public static StudentResponse from(StudentEntity s){
        return new StudentResponse(s.getId(),s.getStudentId(),s.getName(),s.getType(),s.getMajor(),s.getEnrollmentYear(),s.getActive());
    }
}