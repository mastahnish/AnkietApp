package com.solutions.myo.ankietapp;

import android.support.multidex.MultiDexApplication;

import com.solutions.myo.ankietapp.utils.FontOverrideHelper;

public class AnkietAppApplication extends MultiDexApplication {

    private static AnkietAppApplication instance = new AnkietAppApplication();

    public static synchronized AnkietAppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FontOverrideHelper.setCustomFonts(getApplicationContext());
    }
}
