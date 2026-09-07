package university.backend.dto;
import university.backend.domain.entity.CourseEntity;
import java.util.List;
public record CourseResponse(Long id,String courseCode,String courseName,Integer credits,String description,String department,
                             Integer capacity,long activeEnrollmentCount,boolean capacityAvailable,List<String> prerequisiteCodes) {
    public static CourseResponse from(CourseEntity c){
        return new CourseResponse(c.getId(),c.getCourseCode(),c.getCourseName(),c.getCredits(),c.getDescription(),
                c.getDepartment(),c.getCapacity(),c.activeEnrollmentCount(),c.hasCapacity(),c.getPrerequisiteCodes());
    }
}