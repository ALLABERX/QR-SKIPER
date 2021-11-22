package com.allaber.skiper;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.allaber.skiper.activities.main.MainActivity;
import com.allaber.skiper.activities.slider.SliderActivity;
import com.allaber.skiper.activities.start.StartActivity;
import com.allaber.skiper.utils.PreferenceManager;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager preferenceManager = new PreferenceManager(this);
        Intent intent = new Intent(this, MainActivity.class);

        if (preferenceManager.hasQrCode()) {
            intent = new Intent(this, StartActivity.class);
        }

        if (preferenceManager.isFirstLaunch()) {
            intent = new Intent(this, SliderActivity.class);
            preferenceManager.setFirstTimeLaunch(false);
        }
        setAppLanguage();
        intent = new Intent(this, SliderActivity.class);
        startActivity(intent);
        finish();
    }

    public void setAppLanguage() {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        String language = preferenceManager.getAppLanguage();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
    }
}