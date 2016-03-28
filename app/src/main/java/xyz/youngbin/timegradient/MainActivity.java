package xyz.youngbin.timegradient;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Place SettingsFragment
        PreferenceFragment mFragment = new SettingsFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.container, mFragment).commit();
    }
}
