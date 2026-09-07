package university.backend.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name="java_faculty",indexes={
    @Index(name="idx_java_faculty_email",columnList="email",unique=true),
    @Index(name="idx_java_faculty_department",columnList="department_code")
})
public class FacultyEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=160) private String name;
    @Column(nullable=false,unique=true,length=220) private String email;
    @Column(name="department_code",length=20) private String departmentCode;
    @Column(nullable=false) private Boolean active=true;
    @Column(length=120) private String specialization;
    protected FacultyEntity(){}
    public FacultyEntity(String name,String email,String departmentCode,String specialization){
        this.name=name;this.email=email;this.departmentCode=departmentCode;this.specialization=specialization;
    }
    public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;}
    public String getDepartmentCode(){return departmentCode;} public Boolean getActive(){return active;}
    public String getSpecialization(){return specialization;} public void setActive(Boolean v){active=v;}
}