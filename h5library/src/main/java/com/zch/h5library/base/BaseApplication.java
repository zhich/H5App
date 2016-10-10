package com.zch.h5library.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class BaseApplication extends Application {

    public static BaseApplication mBaseApplication;

    public static BaseApplication getInstance() {
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


}
