package university.backend.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScholarshipEligibilityServiceTest {
    @Test void highPerformingStudentGetsFullTier() {
        var decision = new ScholarshipEligibilityService().evaluate(new ScholarshipEligibilityService.Candidate(3.8, 92, 70, 0));
        assertTrue(decision.eligible());
        assertEquals("FULL", decision.tier());
    }
}
