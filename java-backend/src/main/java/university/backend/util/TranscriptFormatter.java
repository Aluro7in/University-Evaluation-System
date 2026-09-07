package university.backend.util;

import university.backend.dto.TranscriptResponse;

public final class TranscriptFormatter {
    private TranscriptFormatter(){}

    public static String plainText(TranscriptResponse t){
        StringBuilder out=new StringBuilder();
        out.append("UNIVERSITY EVALUATION SYSTEM\n");
        out.append("Student: ").append(t.student().name()).append("\n");
        out.append("Student ID: ").append(t.student().studentId()).append("\n");
        out.append("Program Type: ").append(t.student().type()).append("\n");
        out.append("Major: ").append(t.student().major()).append("\n");
        out.append("----------------------------------------\n");
        t.grades().forEach(g->out.append(String.format(
                "%s | %s | %d cr | %d | %s | %.2f%n",
                g.courseCode(),g.courseName(),g.credits(),g.percentage(),g.letter(),g.gpa())));
        out.append("----------------------------------------\n");
        out.append(String.format("GPA: %.2f%n",t.gpa()));
        out.append(String.format("Average: %.2f%%%n",t.averagePercentage()));
        out.append("Standing: ").append(t.academicStanding()).append("\n");
        out.append("Policy: ").append(t.policy()).append("\n");
        return out.toString();
    }
}