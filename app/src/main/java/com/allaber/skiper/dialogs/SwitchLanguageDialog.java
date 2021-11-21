package com.allaber.skiper.dialogs;

import static com.allaber.skiper.utils.Thesaurus.APP_LANGUAGE_SUFFIX_EN;
import static com.allaber.skiper.utils.Thesaurus.APP_LANGUAGE_SUFFIX_RU;
import static com.allaber.skiper.utils.Thesaurus.APP_PREFERENCES;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.allaber.skiper.R;
import com.allaber.skiper.utils.PreferenceManager;

public class SwitchLanguageDialog extends DialogFragment implements View.OnClickListener {

    LinearLayout linearLayoutRu;
    LinearLayout linearLayoutEn;
    TextView textViewApply;
    String language = "en";

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
        View view = inflater.inflate(R.layout.dialog_switch_language, null);

        initiationViewElements(view);
        setOnClickListener();
        setLinearLayoutState();
        builder.setView(view);
        return builder.create();
    }


    private void initiationViewElements(View view) {
        view.findViewById(R.id.textViewCancel).setOnClickListener(this);
        linearLayoutRu = view.findViewById(R.id.linearLayoutRu);
        linearLayoutEn = view.findViewById(R.id.linearLayoutEn);
        textViewApply = view.findViewById(R.id.textViewApply);
    }

    private void setOnClickListener() {
        linearLayoutRu.setOnClickListener(this);
        linearLayoutEn.setOnClickListener(this);
        textViewApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewApply:
                setAppLanguage();
                getDialog().dismiss();
            case R.id.textViewCancel:
                getDialog().dismiss();
                break;
            case R.id.linearLayoutRu:
                setLinearLayoutState("ru");
                break;
            case R.id.linearLayoutEn:
                setLinearLayoutState("en");
                break;
        }
    }

    private void setLinearLayoutState(){
        String languageSuffix = getLanguageSuffix();
        switch (languageSuffix) {
            case APP_LANGUAGE_SUFFIX_EN:
                linearLayoutEn.setEnabled(false);
                break;
            case APP_LANGUAGE_SUFFIX_RU:
                linearLayoutRu.setEnabled(false);
                break;
        }
    }

    private void setLinearLayoutState(String language){
        setLanguage(language);
        switch (language) {
            case APP_LANGUAGE_SUFFIX_EN:
                linearLayoutEn.setEnabled(false);
                linearLayoutRu.setEnabled(true);
                break;
            case APP_LANGUAGE_SUFFIX_RU:
                linearLayoutEn.setEnabled(true);
                linearLayoutRu.setEnabled(false);
                break;
        }
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private String getLanguageSuffix() {
        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        return preferenceManager.getAppLanguage();
    }

    private void setAppLanguage(){
        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        preferenceManager.setAppLanguage(language);
        getDialog().dismiss();
        getActivity().recreate();
    }
}