package com.allaber.skiper.utils;

import static com.allaber.skiper.utils.Thesaurus.APP_LANGUAGE;
import static com.allaber.skiper.utils.Thesaurus.FIRST_LAUNCH;
import static com.allaber.skiper.utils.Thesaurus.APP_PREFERENCES;
import static com.allaber.skiper.utils.Thesaurus.NUMBER_OF_LAUNCHES;
import static com.allaber.skiper.utils.Thesaurus.QR_CODE;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEditor;
    Context context;

    int MODE = 0;

    public PreferenceManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE);
        spEditor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        spEditor.putBoolean(FIRST_LAUNCH, isFirstTime);
        spEditor.apply();
    }

    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(FIRST_LAUNCH, true);
    }

    public boolean hasQrCode() {
        String qrCode = sharedPreferences.getString(QR_CODE, "");
        return Utils.isBlankString(qrCode);
    }
    public void setQrCode(String text) {
        spEditor.putString(QR_CODE, text);
        spEditor.apply();
    }

    public String getQrCode() {
        return sharedPreferences.getString(QR_CODE, "");
    }

    public void setAppLanguage(String language){
        spEditor.putString(APP_LANGUAGE, language);
        spEditor.apply();
    }

    public String getAppLanguage(){
        return sharedPreferences.getString(APP_LANGUAGE, "");
    }

    public int getNumberOfLaunches() {
        return sharedPreferences.getInt(NUMBER_OF_LAUNCHES, 0);
    }

    public void setNumberOfLaunches() {
        int numberOfLaunches = sharedPreferences.getInt(NUMBER_OF_LAUNCHES, 0);
        numberOfLaunches = numberOfLaunches + 1;
        spEditor.putInt(NUMBER_OF_LAUNCHES, numberOfLaunches);
        spEditor.apply();
    }
}