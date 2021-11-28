package com.allaber.skiper.activities.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.allaber.skiper.R;
import com.allaber.skiper.activities.scanner.ScannerActivity;
import com.allaber.skiper.activities.settings.SettingsActivity;
import com.allaber.skiper.dialogs.ManuallyDialogFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            Toast.makeText(this, "Ошибка: разрешения на камеру не получено", Toast.LENGTH_SHORT).show();
        }

    }

    private void showManuallyDialog(){
        ManuallyDialogFragment manuallyDialogFragment = new ManuallyDialogFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        manuallyDialogFragment.show(transaction, "ManuallyDialogFragment");
    }
}