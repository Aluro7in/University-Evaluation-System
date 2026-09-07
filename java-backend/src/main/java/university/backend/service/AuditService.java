package university.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import university.backend.domain.entity.AuditLogEntity;
import university.backend.domain.enums.AuditAction;
import university.backend.repository.AuditLogRepository;
import java.util.List;

@Service
@Transactional
public class AuditService {
    private final AuditLogRepository repository;
    public AuditService(AuditLogRepository repository){this.repository=repository;}

    public void write(AuditAction action,String actor,String entityType,String entityId,String details){
        repository.save(new AuditLogEntity(action,actor,entityType,entityId,details));
    }

    @Transactional(readOnly=true)
    public List<AuditLogEntity> latest(){return repository.findTop100ByOrderByCreatedAtDesc();}
}