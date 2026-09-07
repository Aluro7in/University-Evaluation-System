package university.backend.validation;

import org.springframework.stereotype.Component;
import university.backend.policy.GradeScale;

@Component
public class GradeValidatorService {
    public void validate(Integer percentage){
        if(percentage==null)throw new IllegalArgumentException("Percentage is required");
        GradeScale.validate(percentage);
    }
    public boolean passing(int percentage){validate(percentage);return percentage>=60;}
    public String letter(int percentage){validate(percentage);return GradeScale.toLetter(percentage).name();}
    public double gpa(int percentage){validate(percentage);return GradeScale.toGpa(percentage);}
    public String band(int percentage){validate(percentage);return GradeScale.band(percentage);}
}