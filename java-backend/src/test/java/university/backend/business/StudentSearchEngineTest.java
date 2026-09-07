package university.backend.business;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StudentSearchEngineTest {
    @Test void exactIdRanksFirst() {
        var engine = new StudentSearchEngine();
        var data = List.of(
                new StudentSearchEngine.StudentIndex(1, "ENG001", "Alice Smith", "Computer Science", "Engineering"),
                new StudentSearchEngine.StudentIndex(2, "ENG010", "Alice Brown", "Computer Science", "Engineering"));
        assertEquals(1, engine.search(data, "ENG001").getFirst().student().id());
    }
}
