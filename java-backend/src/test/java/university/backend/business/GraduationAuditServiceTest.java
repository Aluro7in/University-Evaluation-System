package university.backend.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraduationAuditServiceTest {
    @Test void readyCandidateHasNoBlockers() {
        var c = new GraduationAuditService.Candidate(120, 120, 3.1, 20, 20, 86);
        var audit = new GraduationAuditService().audit(c);
        assertTrue(audit.ready());
        assertTrue(audit.blockers().isEmpty());
    }
}
