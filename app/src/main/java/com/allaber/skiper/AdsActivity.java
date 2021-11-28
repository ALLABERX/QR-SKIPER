package com.allaber.skiper;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.allaber.skiper.activities.main.MainActivity;
import com.allaber.skiper.activities.slider.SliderActivity;
import com.allaber.skiper.activities.start.StartActivity;
import com.allaber.skiper.utils.PreferenceManager;
import com.allaber.skiper.utils.common.MyApplication;

import java.util.Locale;

public class AdsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "SplashActivity";

    private static final long COUNTER_TIME = 5;

    private long secondsRemaining;

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferenceManager = new PreferenceManager(this);
        createTimer(COUNTER_TIME);
    }


    private void createTimer(long seconds) {
        final TextView counterTextView = findViewById(R.id.timer);

        CountDownTimer countDownTimer =
                new CountDownTimer(seconds * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        secondsRemaining = ((millisUntilFinished / 1000) + 1);
                        counterTextView.setText(getResources().getString(R.string.string_app_loading) + " " + secondsRemaining);
                    }

                    @Override
                    public void onFinish() {
                        secondsRemaining = 0;
                        counterTextView.setText(getResources().getString(R.string.string_done));

                        Application application = getApplication();

                        if (!(application instanceof MyApplication)) {
                            Log.e(LOG_TAG, "Failed to cast application to MyApplication.");
                            startMainActivity();
                            return;
                        }

                        ((MyApplication) application)
                                .showAdIfAvailable(
                                        AdsActivity.this,
                                        () -> startMainActivity());
                    }
                };
        countDownTimer.start();
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        setAppLanguage();
        if (preferenceManager.hasQrCode()) {
            intent = new Intent(this, StartActivity.class);
        }

        if (preferenceManager.isFirstLaunch()) {
            intent = new Intent(this, SliderActivity.class);
            preferenceManager.setFirstTimeLaunch(false);
        }
        this.startActivity(intent);
    }

    public void setAppLanguage() {
        String language = preferenceManager.getAppLanguage();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
    }
}
