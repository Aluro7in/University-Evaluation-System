package university.backend.analysis;

import java.util.Locale;

/** Simple deterministic relevance scoring for course and student search. */
public final class SearchScorer {
    private SearchScorer(){}

    public static double score(String query,String candidate){
        if(query==null||candidate==null||query.isBlank()||candidate.isBlank())return 0;
        String q=query.toLowerCase(Locale.ROOT).trim();
        String c=candidate.toLowerCase(Locale.ROOT).trim();
        if(c.equals(q))return 1.0;
        if(c.startsWith(q))return 0.9;
        if(c.contains(q))return 0.7;
        int distance=levenshtein(q,c);
        int max=Math.max(q.length(),c.length());
        return max==0?1.0:Math.max(0,1.0-distance/(double)max);
    }

    private static int levenshtein(String a,String b){
        int[] prev=new int[b.length()+1],cur=new int[b.length()+1];
        for(int j=0;j<=b.length();j++)prev[j]=j;
        for(int i=1;i<=a.length();i++){
            cur[0]=i;
            for(int j=1;j<=b.length();j++)
                cur[j]=Math.min(Math.min(cur[j-1]+1,prev[j]+1),prev[j-1]+(a.charAt(i-1)==b.charAt(j-1)?0:1));
            int[] tmp=prev;prev=cur;cur=tmp;
        }
        return prev[b.length()];
    }
}