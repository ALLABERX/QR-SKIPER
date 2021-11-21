package com.allaber.skiper.activities.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.allaber.skiper.R;
import com.allaber.skiper.activities.settings.SettingsActivity;
import com.allaber.skiper.utils.PreferenceManager;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLanguage();
        setContentView(R.layout.activity_main);
        findViewById(R.id.imageViewSettings).setOnClickListener(view -> setActivity(SettingsActivity.class));
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
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

    private void setActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}