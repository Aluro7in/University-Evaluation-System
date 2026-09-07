package university.backend.domain.entity;

import jakarta.persistence.*;
import university.backend.domain.enums.GradeLetter;
import java.time.LocalDateTime;

@Entity
@Table(name="java_grades",indexes=@Index(name="idx_java_grade_enrollment",columnList="enrollment_id",unique=true))
public class GradeEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @OneToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="enrollment_id",nullable=false,unique=true)
    private EnrollmentEntity enrollment;
    @Column(nullable=false) private Integer percentage;
    @Column(nullable=false) private Double gpa;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=12) private GradeLetter letter;
    @Column(length=500) private String instructorNote;
    @Column(nullable=false) private Boolean finalized=false;
    @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
    @Column(nullable=false) private LocalDateTime updatedAt;

    protected GradeEntity(){}
    public GradeEntity(EnrollmentEntity e,Integer p,Double g,GradeLetter l,String note){
        enrollment=e;percentage=p;gpa=g;letter=l;instructorNote=note;
    }
    @PrePersist void prePersist(){LocalDateTime now=LocalDateTime.now();createdAt=now;updatedAt=now;}
    @PreUpdate void preUpdate(){updatedAt=LocalDateTime.now();}
    public Long getId(){return id;} public EnrollmentEntity getEnrollment(){return enrollment;}
    public Integer getPercentage(){return percentage;} public void setPercentage(Integer v){percentage=v;}
    public Double getGpa(){return gpa;} public void setGpa(Double v){gpa=v;} public GradeLetter getLetter(){return letter;}
    public void setLetter(GradeLetter v){letter=v;} public String getInstructorNote(){return instructorNote;}
    public void setInstructorNote(String v){instructorNote=v;} public Boolean getFinalized(){return finalized;}
    public void setFinalized(Boolean v){finalized=v;} public LocalDateTime getCreatedAt(){return createdAt;}
    public LocalDateTime getUpdatedAt(){return updatedAt;}
}