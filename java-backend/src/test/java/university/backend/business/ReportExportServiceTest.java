package university.backend.business;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReportExportServiceTest {
    @Test void csvEscapesValues() {
        var exporter = new ReportExportService();
        assertEquals("A,\"B,C\"", exporter.csvRow(new String[]{"A", "B,C"}));
        assertEquals("A,B", exporter.toCsv(List.of(new String[]{"A","B"})));
    }
}
