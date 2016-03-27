package xyz.youngbin.c3w;

import android.graphics.Color;
import android.graphics.SweepGradient;

import java.util.Calendar;

/**
 * Created by youngbin on 16. 3. 27.
 */
public class Util {
    public static String getStartColor(){
        String time = "#50"+ Util.getCurrentTimeColor();
        return time;
    }
    public static String getEndColor(){
        String time = "#ff"+ Util.getCurrentTimeColor();
        return time;
    }

    static String getCurrentTimeColor(){
        Calendar c = Calendar.getInstance();
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String min = String.valueOf(c.get(Calendar.MINUTE));
        String sec = String.valueOf(c.get(Calendar.SECOND));
        String time = hour+min+sec;
        return time;
    }

    public static float getCurrentTimeInPercentage(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int sec = c.get(Calendar.SECOND);
        float time = sec + (min * 60) + (hour * 60 * 60)/(60*60*24);
        return time-(1/4);
    }
    public static SweepGradient buildGradient(){
        int StartColor = Color.parseColor(Util.getStartColor());
        int EndColor = Color.parseColor(Util.getEndColor());
        int[] Colors = {StartColor, EndColor};
        float[] Poses = {1-Util.getCurrentTimeInPercentage(), getCurrentTimeInPercentage()};
        SweepGradient SG = new SweepGradient(230, 230, Colors, Poses);
        return SG;
    }
}
