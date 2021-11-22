package com.allaber.skiper.activities.manually;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.allaber.skiper.R;
import com.allaber.skiper.activities.main.MainActivity;
import com.allaber.skiper.activities.settings.SettingsActivity;
import com.allaber.skiper.utils.PreferenceManager;
import com.allaber.skiper.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class ManuallyActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonNext;
    TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLanguage();
        setContentView(R.layout.activity_manually);
        textInputEditText = findViewById(R.id.textInputEditText);
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this);

        findViewById(R.id.imageViewSettings2).setOnClickListener(view -> finish());
        findViewById(R.id.imageViewSettings).setOnClickListener(view -> setActivity(SettingsActivity.class));
    }

    @Override
    public void onClick(View view) {
        String text = textInputEditText.getText().toString();
        if(!Utils.isBlankString(text)){
            PreferenceManager preferenceManager = new PreferenceManager(this);
            preferenceManager.setQrCode(text);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }

    private void setActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
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