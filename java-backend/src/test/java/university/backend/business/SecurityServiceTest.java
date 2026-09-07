package university.backend.business;

import org.junit.jupiter.api.Test;
import university.backend.security.*;
import static org.junit.jupiter.api.Assertions.*;

class SecurityServiceTest {
    @Test void facultyCanEditGrades() {
        var auth = new AuthorizationService();
        assertTrue(auth.canEditGrades(Role.FACULTY));
        assertFalse(auth.can(Role.STUDENT, Permission.GRADE_WRITE));
    }
}
