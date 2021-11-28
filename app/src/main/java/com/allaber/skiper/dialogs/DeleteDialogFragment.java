package com.allaber.skiper.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.fragment.app.DialogFragment;

import com.allaber.skiper.R;
import com.allaber.skiper.activities.start.StartActivity;
import com.allaber.skiper.utils.PreferenceManager;

public class DeleteDialogFragment extends DialogFragment implements View.OnClickListener {

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete, null);
        initiationViewElements(view);
        builder.setView(view);
        return builder.create();
    }


    private void initiationViewElements(View view) {
        view.findViewById(R.id.textViewCancel).setOnClickListener(this);
        view.findViewById(R.id.textViewDelete).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewCancel:
                getDialog().dismiss();
                break;
            case R.id.textViewDelete:
                deleteQrCode();
                break;
        }
    }

    private void deleteQrCode(){
        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        preferenceManager.setQrCode(null);
        Intent intent = new Intent(getContext(), StartActivity.class);
        getActivity().startActivity(intent);
        getActivity().finishAffinity();
    }
}