package com.allaber.skiper.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.allaber.skiper.R;
import com.allaber.skiper.activities.start.StartActivity;
import com.allaber.skiper.utils.PreferenceManager;

public class DeleteDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getContext().getResources().getString(R.string.string_confirmation));
        builder.setMessage(getString(R.string.string_delete_qr_code));
        builder.setPositiveButton(getString(R.string.string_delete), (dialog, id) -> {
            PreferenceManager preferenceManager = new PreferenceManager(getContext());
            preferenceManager.setQrCode(null);
            Intent intent = new Intent(getContext(), StartActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        builder.setNegativeButton(getString(R.string.string_cancel), (dialog, id) -> dismiss());
        builder.setCancelable(true);

        return builder.create();
    }
}