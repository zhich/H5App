package com.zch.h5app.plugin;

import android.content.Intent;
import android.os.Bundle;

import com.zch.h5app.activity.BaseActivity;

import org.json.JSONObject;

public abstract class Plugin implements IPlugin {

    protected BaseActivity mContext;

    public void setContext(BaseActivity context) {
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
