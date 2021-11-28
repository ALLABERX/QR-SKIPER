package com.allaber.skiper.activities.slider;

import static com.allaber.skiper.utils.Thesaurus.APP_LANGUAGE_SUFFIX_EN;
import static com.allaber.skiper.utils.Thesaurus.APP_LANGUAGE_SUFFIX_RU;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.allaber.skiper.R;
import com.allaber.skiper.activities.main.MainActivity;
import com.allaber.skiper.activities.slider.adapter.MyViewPagerAdapter;
import com.allaber.skiper.activities.start.StartActivity;
import com.allaber.skiper.dialogs.SwitchLanguageDialog;
import com.allaber.skiper.utils.PreferenceManager;
import com.allaber.skiper.utils.common.BaseActivityActions;

import java.util.Locale;


public class SliderActivity extends AppCompatActivity implements BaseActivityActions, ViewPager.OnPageChangeListener {

    int[] screens = new int[]{R.layout.intro_screen1, R.layout.intro_screen2, R.layout.intro_screen3};
    ImageButton imageButtonLanguage;
    TextView textViewSkip;
    Button buttonNext;
    ViewPager viewPagerStart;
    MyViewPagerAdapter viewPagerAdapter;
    ImageView imageViewPoint1, imageViewPoint2, imageViewPoint3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLanguage();
        initiationViewElements();
        setOnClickListener();
        setIndicatorsAlpha(0);
        setLanguageImage();
    }

    public void setAppLanguage() {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        String language = preferenceManager.getAppLanguage();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
    }

    @Override
    public void initiationViewElements() {
        setContentView(R.layout.activity_slider);
        viewPagerStart = findViewById(R.id.viewPagerStart);
        imageButtonLanguage = findViewById(R.id.imageButtonLanguage);
        textViewSkip = findViewById(R.id.textViewSkip);
        buttonNext = findViewById(R.id.buttonNext);

        imageViewPoint1 = findViewById(R.id.imageViewPoint1);
        imageViewPoint2 = findViewById(R.id.imageViewPoint2);
        imageViewPoint3 = findViewById(R.id.imageViewPoint3);

        viewPagerAdapter = new MyViewPagerAdapter(this, screens);
        viewPagerStart.setAdapter(viewPagerAdapter);
        viewPagerStart.addOnPageChangeListener(this);
    }

    @Override
    public void setOnClickListener() {
        imageButtonLanguage.setOnClickListener(this);
        textViewSkip.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButtonLanguage:
                showLanguageChangeDialog();
                break;
            case R.id.textViewSkip:
                setActivity();
                break;
            case R.id.buttonNext:
                nextScreen();
                break;
        }
    }

    private void showLanguageChangeDialog(){
        SwitchLanguageDialog switchLanguageDialog = new SwitchLanguageDialog();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switchLanguageDialog.show(transaction, "SwitchLanguageDialog");
    }

    private void setLanguageImage() {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        String appLanguage = preferenceManager.getAppLanguage();
        switch (appLanguage) {
            case APP_LANGUAGE_SUFFIX_EN:
                imageButtonLanguage.setImageResource(R.drawable.ic_en);
                break;
            case APP_LANGUAGE_SUFFIX_RU:
                imageButtonLanguage.setImageResource(R.drawable.ic_ru);
                break;
        }
    }

    private void nextScreen() {
        int i = getItem(+1);
        if (i < screens.length) {
            viewPagerStart.setCurrentItem(i);
        } else {
            setActivity();
        }
    }


    private void setIndicatorsAlpha(int position) {
        imageViewPoint1.setImageAlpha(78);
        imageViewPoint2.setImageAlpha(78);
        imageViewPoint3.setImageAlpha(78);

        switch (position) {
            case 0:
                imageViewPoint1.setImageAlpha(255);
                break;
            case 1:
                imageViewPoint2.setImageAlpha(255);
                break;
            case 2:
                imageViewPoint3.setImageAlpha(255);
                break;
        }
    }

    private int getItem(int i) {
        return viewPagerStart.getCurrentItem() + i;
    }

    private void setActivity() {
        PreferenceManager preferenceManager = new PreferenceManager(this);
        Intent intent;
        if (preferenceManager.hasQrCode())
            intent = new Intent(this, StartActivity.class);
        else
            intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onPageSelected(int position) {
        setIndicatorsAlpha(position);
        if (position == screens.length - 1) {
            buttonNext.setText(getResources().getString(R.string.string_ok));
            textViewSkip.setVisibility(View.GONE);
        } else {
            buttonNext.setText(getResources().getString(R.string.string_next));
            textViewSkip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}