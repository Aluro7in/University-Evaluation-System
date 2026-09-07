package university.backend.dto;
import university.backend.domain.entity.FacultyEntity;
public record FacultyResponse(Long id,String name,String email,String departmentCode,Boolean active,String specialization) {
    public static FacultyResponse from(FacultyEntity f){return new FacultyResponse(f.getId(),f.getName(),f.getEmail(),f.getDepartmentCode(),f.getActive(),f.getSpecialization());}
}