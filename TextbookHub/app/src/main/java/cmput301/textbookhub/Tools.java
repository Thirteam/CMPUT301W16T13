package cmput301.textbookhub;

/**
 * Created by Fred on 2016/3/12.
 */
public class Tools {

    public static boolean isStringValid(String s){
        return (s != null && !s.isEmpty());
    }

    public static String roundDecimal(int place, Double num){
        String format_str = "%."+String.valueOf(place)+"f";
        return String.format(format_str, num);
    }

    public static boolean isDouble(String in){
        try{
            Double.parseDouble(in);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
