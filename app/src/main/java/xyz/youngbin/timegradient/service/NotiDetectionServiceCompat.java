package xyz.youngbin.timegradient.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import xyz.youngbin.timegradient.util.BitMapUtil;

public class NotiDetectionServiceCompat extends AccessibilityService {
    Context mContext = NotiDetectionServiceCompat.this;
    private String DEBUGTAG = "SysNotiDetectService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {


        Log.d(DEBUGTAG, "onAccessibilityEvent");
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        Boolean isNotiOn = mPref.getBoolean("display_noti", false);
        if(isNotiOn) {
            // If Event type is TYPE_NOTIFICATION_STATE CHANGED
            if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                System.out.println("notification: " + event.getText());

                // Get App Icon
                final PackageManager pm = getApplicationContext().getPackageManager();
                ApplicationInfo ai;
                try {
                    ai = pm.getApplicationInfo((String) event.getPackageName(), 0);
                } catch (final PackageManager.NameNotFoundException e) {
                    ai = null;
                }
                Drawable appicon = pm.getApplicationIcon(ai);
                //Get Average Color
                int appIconColor = BitMapUtil.getAverageColor(appicon);
                //Save the app color
                SharedPreferences.Editor mPrefEdit
                        = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
                mPrefEdit.putInt("appcolor", appIconColor).apply();
                mPrefEdit.putBoolean("noti", true).apply();

                }
        }
    }

    @Override
    protected void onServiceConnected() {
        System.out.println("onServiceConnected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.notificationTimeout = 100;
        info.feedbackType = AccessibilityEvent.TYPES_ALL_MASK;
        setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {
        System.out.println("onInterrupt");
    }
}