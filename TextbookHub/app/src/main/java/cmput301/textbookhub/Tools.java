package cmput301.textbookhub;

import android.graphics.Bitmap;


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

    /**
     * reduces the size of the image
     * source: http://stackoverflow.com/questions/16954109/reduce-the-size-of-a-bitmap-to-a-specified-size-in-android
     * @param image
     * @param maxSize
     * @return resized bitmap
     */
    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}
