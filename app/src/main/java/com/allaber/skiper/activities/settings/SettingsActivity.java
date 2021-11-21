package com.allaber.skiper.activities.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.allaber.skiper.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewById(R.id.imageViewBack).setOnClickListener(view ->  finish());
    }

}