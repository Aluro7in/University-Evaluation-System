package university.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import university.backend.domain.entity.*;
import university.backend.domain.enums.StudentType;
import university.backend.dto.*;
import university.backend.repository.*;
import university.backend.service.EnrollmentService;
import university.backend.service.GradeService;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seed(@Value("${university.seed-data:true}") boolean enabled,
                            StudentRepository students,CourseRepository courses,
                            DepartmentRepository departments,FacultyRepository faculty,
                            EnrollmentService enrollments,GradeService grades){
        return args->{
            if(!enabled||students.count()>0)return;

            if(!departments.existsByCode("CSE"))
                departments.save(new DepartmentEntity("CSE","Computer Science and Engineering","Engineering"));
            if(!departments.existsByCode("MGT"))
                departments.save(new DepartmentEntity("MGT","Management Studies","Business School"));

            if(faculty.findByEmailIgnoreCase("priya.sharma@example.edu").isEmpty())
                faculty.save(new FacultyEntity("Dr. Priya Sharma","priya.sharma@example.edu","CSE","Software Systems"));

            CourseEntity c1=courses.findByCourseCode("CSE101").orElseGet(
                    ()->courses.save(new CourseEntity("CSE101","Programming Fundamentals",4,
                            "Programming, algorithms, and problem solving","CSE",60)));
            CourseEntity c2=courses.findByCourseCode("CSE201").orElseGet(
                    ()->courses.save(new CourseEntity("CSE201","Database Systems",4,
                            "Relational database design and SQL","CSE",60)));
            c2.setPrerequisiteCodes(java.util.List.of("CSE101"));
            courses.save(c2);
            CourseEntity c3=courses.findByCourseCode("MGT101").orElseGet(
                    ()->courses.save(new CourseEntity("MGT101","Principles of Management",3,
                            "Core management concepts","MGT",60)));

            StudentEntity s1=students.save(new StudentEntity("JAVA001","Alice Johnson",StudentType.ENGINEERING,"Computer Science",2025));
            StudentEntity s2=students.save(new StudentEntity("JAVA002","Bob Khan",StudentType.MANAGEMENT,"Business Management",2025));

            EnrollmentResponse e1=enrollments.enroll(new EnrollmentRequest(s1.getId(),c1.getId(),"2026-FALL"));
            grades.upsert(new GradeRequest(e1.id(),92,"Seed grade",true));

            EnrollmentResponse e2=enrollments.enroll(new EnrollmentRequest(s1.getId(),c2.getId(),"2026-FALL"));
            grades.upsert(new GradeRequest(e2.id(),84,"Seed grade",true));

            EnrollmentResponse e3=enrollments.enroll(new EnrollmentRequest(s2.getId(),c3.getId(),"2026-FALL"));
            grades.upsert(new GradeRequest(e3.id(),88,"Seed grade",true));
        };
    }
}