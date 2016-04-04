package cmput301.textbookhub;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;


/**
 * <code>Tools</code> is used as a general class for methods that might be useful during programming.
 *
 * @author Thirteam
 * @version 1.0
 * @since 2016/03/12
 *
 * Created by Fred on 2016/3/12.
 */
public class Tools {

    /**
     * <code>isStringValid</code> checks if the <code>String</code> is empty or not
     *
     * @param s the <code>String</code> to be tested
     * @return <code>true</code> if the <code>String</code> is valid and <code>false</code> otherwise.
     */
    public static boolean isStringValid(String s){
        return (s != null && !s.isEmpty());
    }

    /**
     * <code>roundDecimal</code> rounds decimals
     *
     * @param place the place to round to (tenths, hundredths, etc)
     * @param num the <code>Double</code> to round
     * @return a <code>String</code> with the final rounded number.
     */
    public static String roundDecimal(int place, Double num){
        String format_str = "%."+String.valueOf(place)+"f";
        return String.format(format_str, num);
    }

    /**
     * <code>isDouble</code> checks if a <code>String</code> is a decimal number
     *
     * @param in the <code>String</code> to be tested.
     * @return <code>true</code> if the <code>String</code> is a decimal number, <code>false</code> otherwise.
     */
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

    public static Double calculateDistanceInMeters(Double latA, Double lngA, Double latB, Double lngB){
        Location locationA = new Location("point A");
        locationA.setLatitude(latA);
        locationA.setLongitude(lngA);
        Location locationB = new Location("point B");
        locationB.setLatitude(latB);
        locationB.setLongitude(lngB);
        Log.i("DIST CALC", latA + "," + lngA + " to " + latB + "," + lngB + " distance is " + locationA.distanceTo(locationB));
        return new Double(locationA.distanceTo(locationB));
    }

}
