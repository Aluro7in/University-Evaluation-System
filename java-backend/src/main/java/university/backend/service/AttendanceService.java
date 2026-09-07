package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.AttendanceEntity;
import university.backend.dto.AttendanceRequest;
import university.backend.dto.AttendanceResponse;
import university.backend.domain.enums.AttendanceStatus;
import university.backend.repository.AttendanceRepository;
import university.backend.util.NumberUtil;

import java.util.List;

@Service
@Transactional
public class AttendanceService {
    private final AttendanceRepository repository;
    private final EnrollmentService enrollments;

    public AttendanceService(AttendanceRepository repository,EnrollmentService enrollments){
        this.repository=repository;this.enrollments=enrollments;
    }

    public AttendanceResponse mark(AttendanceRequest request){
        AttendanceStatus status=AttendanceStatus.valueOf(request.status().trim().toUpperCase());
        var enrollment=enrollments.entity(request.enrollmentId());

        AttendanceEntity row=repository.findByEnrollmentIdAndAttendanceDate(
                request.enrollmentId(),request.attendanceDate())
                .orElseGet(()->new AttendanceEntity(enrollment,request.attendanceDate(),status.name(),request.note()));

        row.setStatus(status.name());
        row.setNote(request.note());
        return AttendanceResponse.from(repository.save(row));
    }

    @Transactional(readOnly=true)
    public List<AttendanceResponse> byEnrollment(Long enrollmentId){
        return repository.findByEnrollmentIdOrderByAttendanceDateDesc(enrollmentId)
                .stream().map(AttendanceResponse::from).toList();
    }

    @Transactional(readOnly=true)
    public double percentage(Long enrollmentId){
        long total=repository.countByEnrollmentId(enrollmentId);
        if(total==0)return 100.0;
        long present=repository.countByEnrollmentIdAndStatus(enrollmentId,"PRESENT")
                +repository.countByEnrollmentIdAndStatus(enrollmentId,"LATE");
        return NumberUtil.round2(NumberUtil.percentage(present,total));
    }

    @Transactional(readOnly=true)
    public boolean meetsMinimum(Long enrollmentId,double minimum){
        return percentage(enrollmentId)>=minimum;
    }

    public void markBatch(Long enrollmentId,List<AttendanceRequest> requests){
        for(AttendanceRequest request:requests){
            if(!request.enrollmentId().equals(enrollmentId))
                throw new IllegalArgumentException("Batch contains a different enrollment ID");
            mark(request);
        }
    }
}