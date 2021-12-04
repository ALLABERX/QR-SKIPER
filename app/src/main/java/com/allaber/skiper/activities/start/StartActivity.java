package com.allaber.skiper.activities.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.allaber.skiper.R;
import com.allaber.skiper.activities.scanner.ScannerActivity;
import com.allaber.skiper.activities.settings.SettingsActivity;
import com.allaber.skiper.dialogs.ManuallyDialogFragment;
import com.allaber.skiper.utils.PreferenceManager;

import java.util.Locale;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLanguage();
        setContentView(R.layout.activity_start);
        findViewById(R.id.buttonManually).setOnClickListener(view -> showManuallyDialog());
        findViewById(R.id.buttonScan).setOnClickListener(view -> getPermissionAndSetActivity());
        findViewById(R.id.imageViewSettings).setOnClickListener(view -> setActivity(SettingsActivity.class));
    }

    private void setActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    private void getPermissionAndSetActivity() {
        int MY_PERMISSIONS_REQUEST_CAMERA = 0;
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            setActivity(ScannerActivity.class);
        } else {
            Toast.makeText(this, getResources().getString(R.string.string_camera_permission), Toast.LENGTH_SHORT).show();
        }

    }

    private void showManuallyDialog() {
        ManuallyDialogFragment manuallyDialogFragment = new ManuallyDialogFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        manuallyDialogFragment.show(transaction, "ManuallyDialogFragment");
    }

    public void setAppLanguage() {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        String language = preferenceManager.getAppLanguage();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, null);
    }


    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager preferenceManager = new PreferenceManager(this);
        if(preferenceManager.hasLanguageBeenChanged())
            recreate();
    }
}

