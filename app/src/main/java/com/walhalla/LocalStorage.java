package com.walhalla;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.walhalla.common.FileUtil;
import com.walhalla.fwdumper.R;
import com.walhalla.ui.fragment.tab.SettingsPreferenceFragment;

import java.io.File;

public class LocalStorage {

    private static LocalStorage instance = null;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    private LocalStorage(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        PreferenceManager.setDefaultValues(context, R.xml.fragment_settings_pref, false);//<-- init settings
        //context.getSharedPreferences("PREF_DATA_44", Context.MODE_PRIVATE);
        editor = mPreferences.edit();
    }

    public synchronized static LocalStorage getInstance(Context context) {
        if (instance == null) {
            instance = new LocalStorage(context);
        }

        return instance;
    }

    public SharedPreferences sharedPreferences() {
        return mPreferences;
    }

    public Object settingsValue(String propertyKey) {
        switch (propertyKey) {

            case SettingsPreferenceFragment.KEY_SD_CARD_LOCATION:
                return saveSdCardLocation();

            case SettingsPreferenceFragment.KEY_REMOTE_HOST:
                return remoteHost();

            case SettingsPreferenceFragment.KEY_REMOTE_PORT:
                return remotePort();

            default:
                return mPreferences.getString(propertyKey, "");
        }
    }




    /**
     * Getters
     */
    public String saveSdCardLocation() {
        return this.mPreferences.getString(SettingsPreferenceFragment.KEY_SD_CARD_LOCATION,
                FileUtil.EXTERNAL_STORAGE_DIRECTORY_ROOT + File.separator);
    }

    public String remoteHost() {
        return this.mPreferences.getString(SettingsPreferenceFragment.KEY_REMOTE_HOST,
                "192.168.0.1");
    }

    public String remotePort() {
        return this.mPreferences.getString(SettingsPreferenceFragment.KEY_REMOTE_PORT, "8888");
    }
}
