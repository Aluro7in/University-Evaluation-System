package university.backend.dto;
import university.backend.domain.entity.DepartmentEntity;
public record DepartmentResponse(Long id,String code,String name,String school,Boolean active) {
    public static DepartmentResponse from(DepartmentEntity d){return new DepartmentResponse(d.getId(),d.getCode(),d.getName(),d.getSchool(),d.getActive());}
}