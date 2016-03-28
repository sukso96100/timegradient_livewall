package xyz.youngbin.timegradient;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by youngbin on 16. 3. 27.
 */
public class Util {
    public static String getEndColor(String mColor){
        String time = "#ff"+ mColor;
        return time;
    }

    static String getCurrentTimeColor(){
        Calendar c = Calendar.getInstance();
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String min = String.valueOf(c.get(Calendar.MINUTE));
        String sec = String.valueOf(c.get(Calendar.SECOND));

        //Check if length is 1
        if(hour.length()<2){hour = "0"+hour;}
        if(min.length()<2){min = "0"+min;}
        if(sec.length()<2){sec = "0"+sec;}
        String time = hour+min+sec;
        Log.d("TIMECOLOR",time);
        return time;
    }

    public static float getCurrentTimeInPercentage(){
        //Convert Cuttent time into degree
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int sec = c.get(Calendar.SECOND);
        Log.d("CUTTENT TIME",String.valueOf(hour)+String.valueOf(min)+String.valueOf(sec));
        Log.d("CT",String.valueOf((sec + (min * 60) + (hour * 3600))));
        int current = (sec + (min * 60) + (hour * 3600));
        float time = (360f/86400f*(float)current - 90f);
        Log.d("TIMEPOS", String.valueOf(time));
        return time;
    }
    public static SweepGradient buildGradient(int width, int height, Context context){
        String TimeColor = Util.getCurrentTimeColor();
        String StartColorString = PreferenceManager.getDefaultSharedPreferences(context).getString("color_end","#000000");
        int StartColor = Color.parseColor(StartColorString);
        int EndColor = Color.parseColor(Util.getEndColor(TimeColor));
        int[] Colors = {StartColor, EndColor};
        float[] Poses = {0, 1};
        SweepGradient SG = new SweepGradient(width/2, height/2, Colors, Poses);
        return SG;
    }
}
