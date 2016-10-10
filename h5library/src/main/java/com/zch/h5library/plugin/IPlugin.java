package com.zch.h5library.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.json.JSONObject;

/**
 * Plugin 根接口，该接口由PluginManager负责管理，该接口用于处理来自Javascript的native调用
 * 实现该类，可根据exec(String action, JSONObject
 * args)中action参数区分调用功能，根据args参数获取native调用d参数 具体使用参见其它子类实现
 *
 * @author chenshang
 */
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
    public void setContext(Fragment context);

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
