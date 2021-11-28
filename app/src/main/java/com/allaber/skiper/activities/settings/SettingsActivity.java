package com.allaber.skiper.activities.settings;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.allaber.skiper.R;
import com.allaber.skiper.dialogs.SwitchLanguageDialog;
import com.allaber.skiper.utils.PreferenceManager;
import com.allaber.skiper.utils.Test;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLanguage();
        setContentView(R.layout.activity_settings);
        findViewById(R.id.imageViewBack).setOnClickListener(view ->  finish());
        findViewById(R.id.linearLayoutLanguage).setOnClickListener(view ->  showLanguageChangeDialog());
    }

    private void showLanguageChangeDialog(){
        SwitchLanguageDialog switchLanguageDialog = new SwitchLanguageDialog();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switchLanguageDialog.show(transaction, "SwitchLanguageDialog");
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