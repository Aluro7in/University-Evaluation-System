package university.backend.business;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ExamResultServiceTest {
    @Test void combinesInternalAndFinalMarks() {
        var result = new ExamResultService().calculate(List.of(new ExamResultService.Assessment("Quiz", 90, 20), new ExamResultService.Assessment("Mid", 80, 40)), 85, 60);
        assertEquals(85, result.finalScore());
        assertEquals("B", result.letter());
        assertTrue(result.passed());
    }
}
