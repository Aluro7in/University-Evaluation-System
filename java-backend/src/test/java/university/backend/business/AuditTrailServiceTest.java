package university.backend.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuditTrailServiceTest {
    @Test void recordsAndFiltersEvents() {
        var audit = new AuditTrailService();
        audit.record("admin", "GRADE_POST", "Grade", "42", "Posted 88");
        audit.record("admin", "UPDATE", "Student", "9", "Changed major");
        assertEquals(1, audit.forEntity("Grade", "42").size());
        assertEquals(1, audit.count("GRADE_POST"));
    }
}
