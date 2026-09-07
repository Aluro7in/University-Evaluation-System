package university.backend.business;

import org.junit.jupiter.api.Test;
import university.backend.academic.AssessmentComponent;
import university.backend.academic.AssessmentService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AssessmentServiceTest {
    @Test void calculatesWeightedScore() {
        var components = List.of(new AssessmentComponent("Quiz", 20, 80), new AssessmentComponent("Exam", 80, 90));
        assertEquals(88, AssessmentService.calculateFinalScore(components));
        assertEquals("B", AssessmentService.classify(88));
    }
    @Test void rejectsBadWeights() {
        var components = List.of(new AssessmentComponent("Exam", 70, 80));
        assertThrows(IllegalArgumentException.class, () -> AssessmentService.calculateFinalScore(components));
    }
}
