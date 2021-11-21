package com.allaber.skiper.fragments.browser;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.allaber.skiper.R;
import com.allaber.skiper.utils.PreferenceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BrowserFragment extends Fragment {

    private ProgressBar progressBar;
    private WebView webView;
    private TextView textViewQrCode;
    private String qrCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        initiationViewElements(view);
        loadQrData();
        return view;
    }

    public void initiationViewElements(View view) {
        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        qrCode = preferenceManager.getQrCode();
        textViewQrCode = view.findViewById(R.id.textViewQrCode);
        webView = view.findViewById(R.id.webView);
        progressBar = view.findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    private void loadQrData() {
        new Thread(() -> {
            try {
                String content = getContent(qrCode);
                webView.post(() -> {
                    webView.loadDataWithBaseURL(qrCode, content, "text/html", "UTF-8", "https://stackoverflow.com/");
                    progressBar.setVisibility(View.INVISIBLE);
                });
            } catch (IOException ex) {
                loadTextQrCode();
            }
        }).start();
    }

    private void loadTextQrCode() {
        getActivity().runOnUiThread(() -> {
            textViewQrCode.setVisibility(View.VISIBLE);
            textViewQrCode.setText(qrCode);
            progressBar.setVisibility(View.INVISIBLE);
        });
    }


    private String getContent(String path) throws IOException {
        BufferedReader reader = null;
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(path);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return (buf.toString());
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    public static BrowserFragment newInstance() {
        return new BrowserFragment();
    }
}