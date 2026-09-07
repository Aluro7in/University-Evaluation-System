package university.backend.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SystemSummaryService {
    private final StudentService students;
    private final CourseService courses;
    private final EnrollmentService enrollments;
    private final GradeService grades;
    private final SemesterService semesters;

    public SystemSummaryService(StudentService students,CourseService courses,
                                EnrollmentService enrollments,GradeService grades,
                                SemesterService semesters){
        this.students=students;this.courses=courses;this.enrollments=enrollments;
        this.grades=grades;this.semesters=semesters;
    }

    public Map<String,Object> snapshot(){
        Map<String,Object> data=new LinkedHashMap<>();
        data.put("students",students.count());
        data.put("activeStudents",students.activeCount());
        data.put("courses",courses.count());
        data.put("enrollments",enrollments.count());
        data.put("grades",grades.count());
        data.put("currentSemester",semesters.currentKey());
        data.put("backend","spring-boot-java");
        data.put("version","2.0");
        return data;
    }
}