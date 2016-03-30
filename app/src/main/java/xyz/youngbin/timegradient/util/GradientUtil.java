package xyz.youngbin.timegradient.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by youngbin on 16. 3. 27.
 */
public class GradientUtil {
    public static String getEndColor(String mColor){
        String time = "#ff"+ mColor;
        return time;
    }

    public static int getStartColor(Context mContext){
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        Boolean isNoti = mPref.getBoolean("noti",false);
        Log.d("isNoti", String.valueOf(isNoti));
        if(isNoti){
            int color = mPref.getInt("appcolor", Color.BLACK);
            mPref.edit().putBoolean("noti",false).apply();
            return color;
        }else {
            return Color.parseColor(mPref.getString("color_end","#000000"));
        }
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
    public static SweepGradient buildGradient(int width, int height, Context mContext){
        String TimeColor = GradientUtil.getCurrentTimeColor();
        int StartColor = GradientUtil.getStartColor(mContext);
        int EndColor = Color.parseColor(GradientUtil.getEndColor(TimeColor));
        int[] Colors = {StartColor, EndColor};
        float[] Poses = {0, 1};
        SweepGradient SG = new SweepGradient(width/2, height/2, Colors, Poses);
        return SG;
    }
}
