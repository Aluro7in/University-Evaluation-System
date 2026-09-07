package university.backend.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="java_attendance",
       indexes=@Index(name="idx_java_attendance_enrollment_date",columnList="enrollment_id,attendance_date",unique=true))
public class AttendanceEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="enrollment_id",nullable=false)
    private EnrollmentEntity enrollment;
    @Column(name="attendance_date",nullable=false) private LocalDate attendanceDate;
    @Column(nullable=false,length=16) private String status;
    @Column(length=400) private String note;
    protected AttendanceEntity(){}
    public AttendanceEntity(EnrollmentEntity e,LocalDate date,String status,String note){
        enrollment=e;attendanceDate=date;this.status=status;this.note=note;
    }
    public Long getId(){return id;} public EnrollmentEntity getEnrollment(){return enrollment;}
    public LocalDate getAttendanceDate(){return attendanceDate;} public String getStatus(){return status;}
    public String getNote(){return note;} public void setStatus(String v){status=v;} public void setNote(String v){note=v;}
}