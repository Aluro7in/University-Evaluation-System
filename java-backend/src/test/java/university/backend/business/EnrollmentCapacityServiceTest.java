package university.backend.business;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentCapacityServiceTest {
    @Test void fullCourseCanEnterWaitlist() {
        var decision = new EnrollmentCapacityService().decide(30, 30, 4, true);
        assertEquals("WAITLIST", decision.state());
        assertTrue(decision.waitListAvailable());
    }
}
