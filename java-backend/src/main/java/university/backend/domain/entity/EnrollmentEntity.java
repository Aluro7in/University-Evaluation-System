package university.backend.domain.entity;

import jakarta.persistence.*;
import university.backend.domain.enums.EnrollmentStatus;
import java.time.LocalDateTime;

@Entity
@Table(name="java_enrollments",indexes={
    @Index(name="idx_java_enrollment_student",columnList="student_id"),
    @Index(name="idx_java_enrollment_course",columnList="course_id")
})
public class EnrollmentEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="student_id",nullable=false) private StudentEntity student;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="course_id",nullable=false) private CourseEntity course;
    @Column(length=30) private String semesterKey;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private EnrollmentStatus status=EnrollmentStatus.ACTIVE;
    @Column(nullable=false,updatable=false) private LocalDateTime enrolledAt;
    @Column(nullable=false) private LocalDateTime updatedAt;

    protected EnrollmentEntity(){}
    public EnrollmentEntity(StudentEntity s,CourseEntity c,String semester){student=s;course=c;semesterKey=semester;}
    @PrePersist void prePersist(){LocalDateTime now=LocalDateTime.now();enrolledAt=now;updatedAt=now;}
    @PreUpdate void preUpdate(){updatedAt=LocalDateTime.now();}
    public Long getId(){return id;} public StudentEntity getStudent(){return student;}
    public void setStudent(StudentEntity v){student=v;} public CourseEntity getCourse(){return course;}
    public void setCourse(CourseEntity v){course=v;} public String getSemesterKey(){return semesterKey;}
    public void setSemesterKey(String v){semesterKey=v;} public EnrollmentStatus getStatus(){return status;}
    public void setStatus(EnrollmentStatus v){status=v;} public LocalDateTime getEnrolledAt(){return enrolledAt;}
    public LocalDateTime getUpdatedAt(){return updatedAt;}
    public boolean isActive(){return status==EnrollmentStatus.ACTIVE;}
}