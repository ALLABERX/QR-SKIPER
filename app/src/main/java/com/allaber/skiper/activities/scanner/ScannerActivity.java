package com.allaber.skiper.activities.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.allaber.skiper.R;
import com.allaber.skiper.activities.main.MainActivity;
import com.allaber.skiper.activities.settings.SettingsActivity;
import com.allaber.skiper.utils.PreferenceManager;
import com.allaber.skiper.utils.common.BaseActivityActions;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

public class ScannerActivity extends AppCompatActivity implements BaseActivityActions {

    private CodeScannerView scannerView;
    private CodeScanner mCodeScanner;
    private ImageView imageViewBack;
    private ImageView imageViewSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initiationViewElements();
        setOnClickListener();
    }

    @Override
    public void initiationViewElements() {
        scannerView = findViewById(R.id.scanner_view);
        scannerView = findViewById(R.id.scanner_view);
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> this.runOnUiThread(() -> setToMainActivity(result.getText())));
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewSettings = findViewById(R.id.imageViewSettings);
    }

    @Override
    public void setOnClickListener() {
        imageViewBack.setOnClickListener(this);
        imageViewSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageViewBack:
                finish();
                break;
            case R.id.imageViewSettings:
                setToMainActivity();
                break;
        }
    }

    private void setToMainActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    private void setToMainActivity(String text){
        PreferenceManager preferenceManager = new PreferenceManager(this);
        preferenceManager.setQrCode(text);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
        PreferenceManager preferenceManager = new PreferenceManager(this);
        if(preferenceManager.hasLanguageBeenChanged())
            recreate();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}