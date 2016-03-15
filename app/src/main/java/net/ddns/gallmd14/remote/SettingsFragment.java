package net.ddns.gallmd14.remote;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by matt on 3/13/16.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
