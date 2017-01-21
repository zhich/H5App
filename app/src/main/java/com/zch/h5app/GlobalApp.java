package com.zch.h5app;

import android.app.Application;

public class GlobalApp extends Application {

    public static GlobalApp mGlobalApp;

    public static GlobalApp getInstance() {
        return mGlobalApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGlobalApp = this;
    }


}
