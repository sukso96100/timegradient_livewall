package xyz.youngbin.timegradient;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.azeesoft.lib.colorpicker.ColorPickerDialog;


/**
 * Created by youngbin on 16. 3. 28.
 */
public class SettingsFragment extends PreferenceFragment{
    Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        addPreferencesFromResource(R.xml.pref);

        //Show app version
        String VersionName = "";
        try {
            VersionName = getActivity().getPackageManager()
                    .getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        final Preference EndColor = findPreference("color_end");
        EndColor.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d("COLORPICKER", "Pref Clicked");
                // Show Color Picker Dialog
                ColorPickerDialog colorPickerDialog = ColorPickerDialog
                        .createColorPickerDialog(mContext, ColorPickerDialog.LIGHT_THEME);
                String prevColor = PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getString("color_end", "#000000");
                Log.d("PREFCOLOR", prevColor);
                colorPickerDialog.setHexaDecimalTextColor(Color.parseColor(prevColor));
                colorPickerDialog.show();
                colorPickerDialog.setOnColorPickedListener(
                        new ColorPickerDialog.OnColorPickedListener() {
                            @Override
                            public void onColorPicked(int color, String hexVal) {
                                //If a color picked, Save Selected Color
                                Log.d("INTCOLOR", String.valueOf(color));
                                Log.d("HEXCOLOR", String.valueOf(hexVal));
                                getPreferenceManager().getSharedPreferences()
                                        .edit().putString("color_end", hexVal).commit();
                            }
                        });
                return true;
            }
        });
        findPreference("app_ver").setSummary(VersionName);

        final CheckBoxPreference DisplayNoti = (CheckBoxPreference)findPreference("display_noti");
        DisplayNoti.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if((Boolean)newValue){
                    // If New Value is true, navigate user to system settings change system settings
                    Log.d("SettingsFragment","New value is - "+newValue);
                    // Navigate user to System Settings
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2){
                        // If user's os is lower than JB_MR2

                        // Show a Toast Message
                        Toast.makeText(mContext,
                                mContext.getResources().getString(R.string.pref_info_accessibility),
                                Toast.LENGTH_LONG).show();

                        // Navigate user to Accessibility Service Settings
                        Intent accessibility = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(accessibility);
                    } else{
                        // If user's os is NOT lower than JB_MR2

                        // Show a Toast Message
                        Toast.makeText(mContext,
                                mContext.getResources().getString(R.string.pref_info_notiaccess),
                                Toast.LENGTH_LONG).show();

                        // Navigate user to Notification Access Settings
                        Intent notiintent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                        startActivity(notiintent);
                    }
                }
                return true;
            }
        });

    }

}