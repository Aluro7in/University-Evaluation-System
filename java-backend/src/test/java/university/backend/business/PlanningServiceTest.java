package university.backend.business;

import org.junit.jupiter.api.Test;
import university.backend.planning.*;
import university.backend.academic.CourseSchedule;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PlanningServiceTest {
    @Test void highGpaGetsExpandedLoad() {
        var plan = new AcademicPlanningService().plan(3.7, 60, 24, List.of("A", "B", "C", "D", "E", "F", "G", "H"));
        assertEquals(21, plan.maxCredits());
        assertTrue(plan.withinLimit());
    }
    @Test void detectsScheduleConflict() {
        var one = new CourseSchedule("A", DayOfWeek.MONDAY, LocalTime.of(9,0), LocalTime.of(10,0), "R1");
        var two = new CourseSchedule("B", DayOfWeek.MONDAY, LocalTime.of(9,30), LocalTime.of(10,30), "R2");
        assertEquals(1, ConflictDetector.findConflicts(List.of(one, two)).size());
    }
}
