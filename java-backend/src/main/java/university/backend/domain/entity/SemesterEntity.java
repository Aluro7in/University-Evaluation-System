package university.backend.domain.entity;

import jakarta.persistence.*;
import university.backend.domain.enums.SemesterType;
import java.time.LocalDate;

@Entity
@Table(name="java_semesters",indexes=@Index(name="idx_java_semester_key",columnList="academic_year,term",unique=true))
public class SemesterEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="academic_year",nullable=false) private Integer academicYear;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=16) private SemesterType term;
    @Column(nullable=false) private LocalDate startDate;
    @Column(nullable=false) private LocalDate endDate;
    @Column(nullable=false) private Boolean current=false;

    protected SemesterEntity(){}
    public SemesterEntity(Integer year,SemesterType term,LocalDate start,LocalDate end,Boolean current){
        academicYear=year;this.term=term;startDate=start;endDate=end;this.current=current;
    }
    public Long getId(){return id;} public Integer getAcademicYear(){return academicYear;}
    public SemesterType getTerm(){return term;} public LocalDate getStartDate(){return startDate;}
    public LocalDate getEndDate(){return endDate;} public Boolean getCurrent(){return current;}
    public void setCurrent(Boolean v){current=v;} public String key(){return academicYear+"-"+term.name();}
    public boolean contains(LocalDate d){return !d.isBefore(startDate)&&!d.isAfter(endDate);}
}