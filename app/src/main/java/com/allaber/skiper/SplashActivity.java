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

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(this);
        setAppLanguage();
        Intent intent = new Intent(this, MainActivity.class);


        if(isFreePeriodHasEnded()){
            intent = new Intent(this, AdsActivity.class);
            this.startActivity(intent);
        }

        if (preferenceManager.hasQrCode()) {
            intent = new Intent(this, StartActivity.class);
        }

        if (preferenceManager.isFirstLaunch()) {
            intent = new Intent(this, SliderActivity.class);
            preferenceManager.setFirstTimeLaunch(false);
        }


        this.startActivity(intent);
    }

    private boolean isFreePeriodHasEnded(){
        int numberOfLaunches = preferenceManager.getNumberOfLaunches();
        preferenceManager.setNumberOfLaunches();
        return numberOfLaunches > 2;
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