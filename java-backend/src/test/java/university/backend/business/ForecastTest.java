package university.backend.business;

import org.junit.jupiter.api.Test;
import university.backend.forecast.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ForecastTest {
    @Test void trendIsDeterministic() {
        var result = new PerformanceForecastService().forecast(List.of(2.0, 2.4, 2.8), 3.2);
        assertEquals(2.8, result.currentGpa());
        assertTrue(result.projectedGpa() > 2.8);
        assertEquals("IMPROVING", result.direction());
    }
    @Test void retentionRiskIsBounded() {
        var score = new RetentionAnalyzer().score(1.2, 50, 45, 4);
        assertTrue(score.score() >= 70);
        assertTrue(score.highRisk());
    }
}
