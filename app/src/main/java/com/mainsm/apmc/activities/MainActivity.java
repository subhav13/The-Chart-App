package com.mainsm.apmc.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.mainsm.apmc.R;
import com.mainsm.apmc.constants.Constants;
import com.mainsm.apmc.viewmodel.HomeViewModel;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getStates(null);
        getLocalPrefValue();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }, 2000);



    }

    private void getLocalPrefValue() {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = getApplication().getSharedPreferences(Constants.DEFAULT, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String localCode = sharedPreferences.getString(Constants.LOCAL,"null");
        if(localCode.equals("null"))
            setAppLocale(localCode);
    }

    @SuppressLint("ObsoleteSdkInt")
    private void setAppLocale(String localeCode){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }
}