package com.allaber.skiper.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.allaber.skiper.R;
import com.allaber.skiper.activities.main.MainActivity;
import com.allaber.skiper.utils.PreferenceManager;
import com.allaber.skiper.utils.Utils;

public class ManuallyDialogFragment extends DialogFragment implements View.OnClickListener {

    EditText textInputEditText;

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
                     View view = inflater.inflate(R.layout.dialog_manually, null);
        initiationViewElements(view);
        builder.setView(view);
        return builder.create();
    }


    private void initiationViewElements(View view) {
        view.findViewById(R.id.textViewCancel).setOnClickListener(this);
        view.findViewById(R.id.textViewAdd).setOnClickListener(this);
        textInputEditText = view.findViewById(R.id.textInputEditText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewCancel:
                getDialog().dismiss();
                break;
            case R.id.textViewAdd:
                setQrCode();
                break;
        }
    }

    private void setQrCode(){
        String qrCode = textInputEditText.getText().toString();
        if(!Utils.isBlankString(qrCode)){
            PreferenceManager preferenceManager = new PreferenceManager(getContext());
            preferenceManager.setQrCode(qrCode);
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finishAffinity();
        }
    }

}