package xyz.youngbin.timegradient.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.service.wallpaper.WallpaperService;
import android.util.Log;

import xyz.youngbin.timegradient.TimeGradientService;
import xyz.youngbin.timegradient.util.BitMapUtil;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotiDetectionService extends NotificationListenerService {
    String TAG = getClass().getSimpleName();
    Context mContext = NotiDetectionService.this;
    public NotiDetectionService() {
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++");
        Log.d(TAG, "onNotificationRemoved");

    }

    //When new notification posted on status ber
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.d(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++");
        Log.d(TAG, "onNotificationPosted");

        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        Boolean isNotiOn = mPref.getBoolean("display_noti", false);
        if(isNotiOn) {
            // Get App Icon
            final PackageManager pm = getApplicationContext().getPackageManager();
            ApplicationInfo ai;
            try {
                ai = pm.getApplicationInfo(sbn.getPackageName(), 0);
            } catch (final PackageManager.NameNotFoundException e) {
                ai = null;
            }
            Drawable appicon = pm.getApplicationIcon(ai);
            //Get Average Color of the icon
            int appIconColor = BitMapUtil.getAverageColor(appicon);

            Log.d(TAG, "Saving App Color");
            //Save the app color
            SharedPreferences.Editor mPrefEdit
                    = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
            mPrefEdit.putInt("appcolor", appIconColor).apply();
            mPrefEdit.putBoolean("noti", true).apply();
        }
    }


//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
