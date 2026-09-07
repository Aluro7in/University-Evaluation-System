package university.backend.domain.entity;

import jakarta.persistence.*;
import university.backend.domain.enums.AuditAction;
import java.time.LocalDateTime;

@Entity
@Table(name="java_audit_logs",indexes=@Index(name="idx_java_audit_created",columnList="created_at"))
public class AuditLogEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=24) private AuditAction action;
    @Column(nullable=false,length=80) private String actor;
    @Column(length=80) private String entityType;
    @Column(length=120) private String entityId;
    @Column(length=1200) private String details;
    @Column(name="created_at",nullable=false,updatable=false) private LocalDateTime createdAt;
    protected AuditLogEntity(){}
    public AuditLogEntity(AuditAction action,String actor,String entityType,String entityId,String details){
        this.action=action;this.actor=actor;this.entityType=entityType;this.entityId=entityId;this.details=details;
    }
    @PrePersist void prePersist(){createdAt=LocalDateTime.now();}
    public Long getId(){return id;} public AuditAction getAction(){return action;} public String getActor(){return actor;}
    public String getEntityType(){return entityType;} public String getEntityId(){return entityId;} public String getDetails(){return details;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}