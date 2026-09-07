package university.backend.importer;

import java.util.ArrayList;
import java.util.List;

/** Validates simple student import rows before persistence. */
public class StudentImportValidator {
    public ImportResult validate(List<String[]> rows) {
        int accepted = 0;
        int rejected = 0;
        List<ImportIssue> issues = new ArrayList<>();
        if (rows == null) return new ImportResult(0, 0, issues);

        for (int index = 0; index < rows.size(); index++) {
            String[] row = rows.get(index);
            int rowNumber = index + 1;
            boolean valid = true;
            if (row == null || row.length < 5) {
                issues.add(new ImportIssue(rowNumber, "row", "Expected at least 5 columns"));
                valid = false;
            } else {
                if (blank(row[0])) { issues.add(new ImportIssue(rowNumber, "studentId", "Student ID is required")); valid = false; }
                if (blank(row[1])) { issues.add(new ImportIssue(rowNumber, "name", "Name is required")); valid = false; }
                if (!"ENGINEERING".equalsIgnoreCase(row[2]) && !"MANAGEMENT".equalsIgnoreCase(row[2])) {
                    issues.add(new ImportIssue(rowNumber, "type", "Type must be ENGINEERING or MANAGEMENT")); valid = false;
                }
                try { Integer.parseInt(row[3]); } catch (Exception e) {
                    issues.add(new ImportIssue(rowNumber, "enrollmentYear", "Enrollment year must be numeric")); valid = false;
                }
                if (blank(row[4])) { issues.add(new ImportIssue(rowNumber, "major", "Major is required")); valid = false; }
            }
            if (valid) accepted++; else rejected++;
        }
        return new ImportResult(accepted, rejected, issues);
    }

    private boolean blank(String value) { return value == null || value.isBlank(); }
}
