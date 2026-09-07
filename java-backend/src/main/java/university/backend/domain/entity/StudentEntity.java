package university.backend.domain.entity;

import jakarta.persistence.*;
import university.backend.domain.enums.StudentType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="java_students", indexes={
    @Index(name="idx_java_student_student_id", columnList="student_id", unique=true),
    @Index(name="idx_java_student_type", columnList="type")
})
public class StudentEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="student_id", nullable=false, unique=true, length=64)
    private String studentId;
    @Column(nullable=false, length=180) private String name;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private StudentType type;
    @Column(length=160) private String major;
    @Column(nullable=false) private Integer enrollmentYear;
    @Column(nullable=false) private Boolean active=true;
    @Column(nullable=false, updatable=false) private LocalDateTime createdAt;
    @Column(nullable=false) private LocalDateTime updatedAt;

    @OneToMany(mappedBy="student", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<EnrollmentEntity> enrollments=new ArrayList<>();

    protected StudentEntity() {}
    public StudentEntity(String studentId,String name,StudentType type,String major,Integer enrollmentYear){
        this.studentId=studentId; this.name=name; this.type=type; this.major=major;
        this.enrollmentYear=enrollmentYear; this.active=true;
    }
    @PrePersist void prePersist(){ LocalDateTime now=LocalDateTime.now(); createdAt=now; updatedAt=now; }
    @PreUpdate void preUpdate(){ updatedAt=LocalDateTime.now(); }

    public Long getId(){return id;}
    public String getStudentId(){return studentId;}
    public void setStudentId(String v){studentId=v;}
    public String getName(){return name;}
    public void setName(String v){name=v;}
    public StudentType getType(){return type;}
    public void setType(StudentType v){type=v;}
    public String getMajor(){return major;}
    public void setMajor(String v){major=v;}
    public Integer getEnrollmentYear(){return enrollmentYear;}
    public void setEnrollmentYear(Integer v){enrollmentYear=v;}
    public Boolean getActive(){return active;}
    public void setActive(Boolean v){active=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public LocalDateTime getUpdatedAt(){return updatedAt;}
    public List<EnrollmentEntity> getEnrollments(){return enrollments;}
    public void addEnrollment(EnrollmentEntity e){enrollments.add(e);e.setStudent(this);}
    public void removeEnrollment(EnrollmentEntity e){enrollments.remove(e);e.setStudent(null);}
}