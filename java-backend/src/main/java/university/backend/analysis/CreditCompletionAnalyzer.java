package university.backend.analysis;

import java.util.List;

/** Measures degree progress against a target credit requirement. */
public final class CreditCompletionAnalyzer {
    private CreditCompletionAnalyzer() {}

    public record Progress(int earned,int required,double percentage,int remaining,String status) {}

    public static Progress analyze(int earned,int required) {
        if(required<=0)throw new IllegalArgumentException("Required credits must be positive");
        int safeEarned=Math.max(0,earned);
        double pct=Math.min(100,Math.round(safeEarned*10000.0/required)/100.0);
        int remaining=Math.max(0,required-safeEarned);
        String status=safeEarned>=required?"COMPLETED":pct>=75?"FINAL_STAGE":pct>=50?"ON_TRACK":"EARLY_STAGE";
        return new Progress(safeEarned,required,pct,remaining,status);
    }

    public static int semestersRequired(int remainingCredits,int averageSemesterCredits){
        if(remainingCredits<=0)return 0;
        if(averageSemesterCredits<=0)throw new IllegalArgumentException("Average semester credits must be positive");
        return (int)Math.ceil(remainingCredits/(double)averageSemesterCredits);
    }
}