package university.backend.academic;

public record AcademicDecision(
        boolean allowed,
        String decision,
        String reason
) {}
