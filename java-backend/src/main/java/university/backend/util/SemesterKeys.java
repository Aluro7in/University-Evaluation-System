package university.backend.util;

import university.backend.domain.enums.SemesterType;

public final class SemesterKeys {
    private SemesterKeys(){}

    public static String of(int year,SemesterType type){return year+"-"+type.name();}
    public static boolean valid(String key){return key!=null&&key.matches("\\d{4}-(SPRING|SUMMER|FALL|WINTER)");}
    public static int year(String key){return Integer.parseInt(key.substring(0,4));}
    public static SemesterType type(String key){return SemesterType.valueOf(key.substring(5));}
    public static int compare(String left,String right){return left.compareTo(right);}
}