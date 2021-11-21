package com.allaber.skiper.fragments.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.allaber.skiper.R;
import com.allaber.skiper.dialogs.DeleteDialogFragment;
import com.allaber.skiper.utils.PreferenceManager;
import com.allaber.skiper.utils.common.BaseFragmentActions;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class HomeFragment extends Fragment implements BaseFragmentActions {

    ImageView imageViewQR;
    Button buttonRemove;
    Button buttonShare;
    Button buttonCopy;
    String qrCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initiationViewElements(view);
        setOnClickListener();
        setImageViewQR();
        return view;
    }

    @Override
    public void initiationViewElements(View view) {
        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        qrCode = preferenceManager.getQrCode();
        buttonRemove = view.findViewById(R.id.buttonRemove);
        buttonShare = view.findViewById(R.id.buttonShare);
        buttonCopy = view.findViewById(R.id.buttonCopy);
        imageViewQR = view.findViewById(R.id.imageViewQR);
    }

    @Override
    public void setOnClickListener() {
        buttonRemove.setOnClickListener(this);
        buttonShare.setOnClickListener(this);
        buttonCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRemove:
                removeQrCode();
                break;
            case R.id.buttonShare:
                shareQrCode();
                break;
            case R.id.buttonCopy:
                copyQrCode();
                break;
        }

    }

    private void removeQrCode() {
        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        deleteDialogFragment.show(transaction, "dialog");
    }

    private void shareQrCode() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = qrCode;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Я поделился своим qr кодом в приложении QR-SKIPER\n\n");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.string_share_qr_code)));
    }

    private void copyQrCode() {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.string_qr_code), qrCode);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), getString(R.string.string_copied), Toast.LENGTH_SHORT).show();
    }

    private void setImageViewQR() {
        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        String qrCode = preferenceManager.getQrCode();
        QRGEncoder qrgEncoder = new QRGEncoder(qrCode, null, QRGContents.Type.TEXT, 1000);
        qrgEncoder.setColorBlack(Color.BLACK);
        Bitmap bitmap = qrgEncoder.getBitmap();
        imageViewQR.setImageBitmap(bitmap);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


}