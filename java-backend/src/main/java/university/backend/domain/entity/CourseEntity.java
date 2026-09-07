package university.backend.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import university.backend.domain.enums.EnrollmentStatus;

@Entity
@Table(name="java_courses", indexes={
    @Index(name="idx_java_course_code", columnList="course_code", unique=true),
    @Index(name="idx_java_course_department", columnList="department")
})
public class CourseEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="course_code",nullable=false,unique=true,length=32) private String courseCode;
    @Column(nullable=false,length=200) private String courseName;
    @Column(nullable=false) private Integer credits;
    @Column(length=600) private String description;
    @Column(length=120) private String department;
    @Column(nullable=false) private Integer capacity=60;
    @Column(nullable=false) private Boolean active=true;
    @ElementCollection @CollectionTable(name="java_course_prerequisites",joinColumns=@JoinColumn(name="course_id"))
    @Column(name="prerequisite_code",length=32) private List<String> prerequisiteCodes=new ArrayList<>();
    @OneToMany(mappedBy="course") private List<EnrollmentEntity> enrollments=new ArrayList<>();
    @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
    @Column(nullable=false) private LocalDateTime updatedAt;

    protected CourseEntity(){}
    public CourseEntity(String code,String name,Integer credits,String description,String department,Integer capacity){
        courseCode=code; courseName=name; this.credits=credits; this.description=description;
        this.department=department; this.capacity=capacity; active=true;
    }
    @PrePersist void prePersist(){LocalDateTime now=LocalDateTime.now();createdAt=now;updatedAt=now;}
    @PreUpdate void preUpdate(){updatedAt=LocalDateTime.now();}
    public Long getId(){return id;} public String getCourseCode(){return courseCode;}
    public void setCourseCode(String v){courseCode=v;} public String getCourseName(){return courseName;}
    public void setCourseName(String v){courseName=v;} public Integer getCredits(){return credits;}
    public void setCredits(Integer v){credits=v;} public String getDescription(){return description;}
    public void setDescription(String v){description=v;} public String getDepartment(){return department;}
    public void setDepartment(String v){department=v;} public Integer getCapacity(){return capacity;}
    public void setCapacity(Integer v){capacity=v;} public Boolean getActive(){return active;}
    public void setActive(Boolean v){active=v;} public List<String> getPrerequisiteCodes(){return prerequisiteCodes;}
    public void setPrerequisiteCodes(List<String> v){prerequisiteCodes=v==null?new ArrayList<>():new ArrayList<>(v);}
    public List<EnrollmentEntity> getEnrollments(){return enrollments;}
    public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
    public long activeEnrollmentCount(){
        return enrollments.stream().filter(e->e.getStatus()==EnrollmentStatus.ACTIVE).count();
    }
    public boolean hasCapacity(){return activeEnrollmentCount()<capacity;}
}