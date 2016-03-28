package xyz.youngbin.c3w;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

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
        String VersionName = "";
        try {
            VersionName = getActivity().getPackageManager()
                    .getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        final Preference EndColor = getPreferenceManager().findPreference("color_end");
        EndColor.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d("COLORPICKER", "Pref Clicked");
                ColorPickerDialog colorPickerDialog = ColorPickerDialog
                        .createColorPickerDialog(mContext, ColorPickerDialog.LIGHT_THEME);
                String prevColor = PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getString("color_end","#000000");
                Log.d("PREFCOLOR",prevColor);
                colorPickerDialog.setHexaDecimalTextColor(Color.parseColor(prevColor));
                colorPickerDialog.show();
                colorPickerDialog.setOnColorPickedListener(
                        new ColorPickerDialog.OnColorPickedListener() {
                            @Override
                            public void onColorPicked(int color, String hexVal) {
                                //Save Selected Color
                                Log.d("INTCOLOR", String.valueOf(color));
                                Log.d("HEXCOLOR", String.valueOf(hexVal));
                                getPreferenceManager().getSharedPreferences()
                                        .edit().putString("color_end",hexVal).commit();
                            }
                        });
                return true;
            }
                });
        getPreferenceManager().findPreference("app_ver").setSummary(VersionName);

    }

}