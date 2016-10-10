package com.zch.h5library.plugin;

import android.os.Handler;
import android.webkit.WebView;

import org.json.JSONObject;

public interface AsynServiceHandler extends Runnable {
    public void setService(String service);

    public void setAction(String action);

    public void setArgs(JSONObject args);

    public void setWebView(WebView webView);

    public void setRequestID(String requestID);

    public void setMessageHandler(Handler handler);

    public void setPluginManager(PluginManager pluginManager);
}
