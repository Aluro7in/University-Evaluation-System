package university.backend.util;

public final class NumberUtil {
    private NumberUtil(){}

    public static double round2(double value){return Math.round(value*100.0)/100.0;}
    public static double percentage(double value,double total){
        if(total<=0)return 0;
        return round2(value*100.0/total);
    }
    public static int clamp(int value,int min,int max){return Math.max(min,Math.min(max,value));}
    public static double clamp(double value,double min,double max){return Math.max(min,Math.min(max,value));}
    public static double ratio(double numerator,double denominator){
        return denominator==0?0:round2(numerator/denominator);
    }
}