package university.backend.util;

public final class KeyNormalizer {
    private KeyNormalizer(){}

    public static String code(String value){
        return value==null?null:value.trim().toUpperCase();
    }
    public static String name(String value){
        if(value==null)return null;
        return value.trim().replaceAll("\\s+"," ");
    }
    public static String optional(String value){
        if(value==null)return null;
        String normalized=value.trim();
        return normalized.isEmpty()?null:normalized;
    }
    public static boolean blank(String value){
        return value==null||value.trim().isEmpty();
    }
    public static String semester(String value){
        return code(value);
    }
}