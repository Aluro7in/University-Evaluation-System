package university.backend.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AttendancePolicyServiceTest {
    @Test void criticalAttendanceNeedsAdvisor() {
        var service = new AttendancePolicyService();
        var summary = new AttendancePolicyService.AttendanceSummary(4, 0, 6, 0);
        assertEquals("CRITICAL", service.status(summary));
        assertEquals(true, service.recommendation(summary).get("requiresAdvisor"));
    }
}
