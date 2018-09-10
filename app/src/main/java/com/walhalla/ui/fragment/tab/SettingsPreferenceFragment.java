package com.walhalla.ui.fragment.tab;


import android.os.Bundle;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.walhalla.LocalStorage;
import com.walhalla.fwdumper.R;

public class SettingsPreferenceFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {


    /**
     * Local storage
     */
    public static final String KEY_SD_CARD_LOCATION = "key_sdcard_location";

    /**
     * Remote storage
     */
    public static final String KEY_REMOTE_HOST = "key_remote_host";
    public static final String KEY_REMOTE_PORT = "key_remote_port";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.fragment_settings_pref);
        EditTextPreference p1 = (EditTextPreference) findPreference(KEY_SD_CARD_LOCATION);
        EditTextPreference p2 = (EditTextPreference) findPreference(KEY_REMOTE_HOST);
        EditTextPreference p3 = (EditTextPreference) findPreference(KEY_REMOTE_PORT);


        bindPreferenceSummaryToValue(p1, p2);


        /**
         * Default port
         */
        String port = LocalStorage.getInstance(getContext()).remotePort();
        this.onPreferenceChange(p3, port);

        p3.setOnPreferenceChangeListener((preference, newValue) -> {
            int val = 8888;
            try {
                val = Integer.parseInt(newValue.toString().trim());
                if ((val > 1) && (val < 65535)) {

                    p3.setSummary("" +val);
                    p3.setText("" + val);
                    return true;
                } else {
                    // invalid you can show invalid message
                    Toast.makeText(getContext(), "error text", Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        });


//        CheckBoxPreference cbGoogleDrive = (CheckBoxPreference) findPreference(KEY_GOOGLE_DRIVE_STORAGE);
//        bindPreferenceSummaryToValue(cbGoogleDrive);
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }


    private void bindPreferenceSummaryToValue(Preference... preferences) {
        for (Preference preference : preferences) {
            preference.setOnPreferenceChangeListener(this/*listener*/);


            //Устанавливаем настройки из сохраненных в LocalStorage
            Object obj = LocalStorage.getInstance(getContext()).settingsValue(preference.getKey());
            //String obj = mPreferences.getString(preferences.getKey(),"");//Crash if boolean
            /*listener*/
            this.onPreferenceChange(preference, obj);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();
        if (preference instanceof EditTextPreference) {
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            editTextPreference.setSummary(stringValue);
            editTextPreference.setText(stringValue);


        } else if (preference instanceof CheckBoxPreference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            checkBoxPreference.setChecked((Boolean) newValue);
        } else {
            preference.setSummary("Wtf");
        }
        return true;
    }
}
