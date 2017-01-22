package com.zch.h5app.plugin;

import android.content.Intent;
import android.os.Bundle;

import com.zch.h5app.activity.BaseActivity;

import org.json.JSONObject;

public interface IPlugin {

    public static final String SERVICE = "service";
    public static final String ACTION = "action";
    public static final String ARGS = "args";

    /**
     * 执行请求
     *
     * @param action 功能
     * @param args   参数
     * @return pluginResult 结果
     */
    public PluginResult exec(String action, JSONObject args)
            throws ActionNotFoundException;

    public PluginResult execAsyn(String action, JSONObject args, String requestID)
            throws ActionNotFoundException;

    /**
     * 设置DroidHtml5 Context
     *
     * @param context
     */
    public void setContext(BaseActivity context);

    public void onActivityResult(int requestCode, int resultCode, Intent data);

    public void onCreate(Bundle savedInstanceState);

    public void onDestroy();

    public void onPause();

    public void onRestart();

    public void onResume();

    public void onSaveInstanceState(Bundle outState);

    public void onStart();

    public void onStop();

}
