package university.backend.analysis;

import java.util.List;

/** Compares evaluation policies for audit and explanation screens. */
public final class PolicyComparator {
    private PolicyComparator(){}

    public record Comparison(double first,double second,double difference,String higher) {}

    public static Comparison compare(List<Double> first,List<Double> second){
        double a=average(first),b=average(second);
        double d=Math.round((a-b)*100.0)/100.0;
        String higher=d>0?"FIRST":d<0?"SECOND":"EQUAL";
        return new Comparison(a,b,d,higher);
    }

    public static double weightedAverage(List<Double> values,List<Integer> weights){
        if(values==null||weights==null||values.size()!=weights.size()||values.isEmpty())
            return 0;
        double numerator=0;int denominator=0;
        for(int i=0;i<values.size();i++){numerator+=values.get(i)*weights.get(i);denominator+=weights.get(i);}
        return denominator==0?0:numerator/denominator;
    }

    private static double average(List<Double> values){
        return values==null||values.isEmpty()?0:values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }
}