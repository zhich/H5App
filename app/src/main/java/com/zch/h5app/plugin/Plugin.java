package com.zch.h5app.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

public abstract class Plugin implements IPlugin {

    protected Context mContext;

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override
    public PluginResult execAsyn(String action, JSONObject args, String requestID) throws ActionNotFoundException {
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onRestart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
