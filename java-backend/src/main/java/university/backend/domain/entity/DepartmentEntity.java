package university.backend.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="java_departments",indexes=@Index(name="idx_java_department_code",columnList="code",unique=true))
public class DepartmentEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,unique=true,length=20) private String code;
    @Column(nullable=false,length=160) private String name;
    @Column(length=200) private String school;
    @Column(nullable=false) private Boolean active=true;
    @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
    protected DepartmentEntity(){}
    public DepartmentEntity(String code,String name,String school){this.code=code;this.name=name;this.school=school;}
    @PrePersist void prePersist(){createdAt=LocalDateTime.now();}
    public Long getId(){return id;} public String getCode(){return code;} public String getName(){return name;}
    public String getSchool(){return school;} public Boolean getActive(){return active;} public void setActive(Boolean v){active=v;}
}