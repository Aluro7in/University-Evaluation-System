package university.backend.policy;

import university.backend.domain.entity.CourseEntity;
import university.backend.domain.entity.GradeEntity;
import university.backend.domain.enums.AcademicStanding;
import java.util.*;
import java.util.stream.Collectors;

public final class AcademicRules {
    private AcademicRules(){}

    public static boolean passes(int percentage){ return percentage>=60; }

    public static AcademicStanding standing(double gpa){
        if(gpa>=3.5)return AcademicStanding.EXCELLENT;
        if(gpa>=3.0)return AcademicStanding.GOOD;
        if(gpa>=2.0)return AcademicStanding.SATISFACTORY;
        if(gpa>=1.0)return AcademicStanding.PROBATION;
        return AcademicStanding.AT_RISK;
    }

    public static boolean deanListEligible(double gpa,int credits){
        return gpa>=3.5 && credits>=12;
    }

    public static int totalCredits(Collection<GradeEntity> grades){
        if(grades==null)return 0;
        return grades.stream().filter(Objects::nonNull)
                .filter(g->g.getEnrollment()!=null && g.getEnrollment().getCourse()!=null)
                .mapToInt(g->g.getEnrollment().getCourse().getCredits()).sum();
    }

    public static boolean prerequisitesSatisfied(CourseEntity course,Collection<String> completed){
        if(course==null||course.getPrerequisiteCodes()==null||course.getPrerequisiteCodes().isEmpty())return true;
        Set<String> set=(completed==null?Set.<String>of():completed.stream().filter(Objects::nonNull)
                .map(String::trim).map(String::toUpperCase).collect(Collectors.toSet()));
        return set.containsAll(course.getPrerequisiteCodes().stream().filter(Objects::nonNull)
                .map(String::trim).map(String::toUpperCase).toList());
    }
}