package com.zch.h5library.plugin;

import android.os.Handler;
import android.webkit.WebView;

import com.zch.h5library.base.BaseFragment;

import org.json.JSONObject;


/**
 * 处理原生异步交互
 *
 * @author samland, 2013/11/11
 * @version 1.0
 */
public class AsynServiceHandlerImpl implements AsynServiceHandler {

    private String service;
    private String action;
    private JSONObject args;
    private WebView webView;
    private Handler handler;
    private String requestID;
    private PluginManager pluginManager;

    @Override
    public void run() {

        try {
            final String responseBody = pluginManager.getInstance().execAsyn(
                    service,
                    action,
                    args, requestID);
            if (responseBody != null) {
                handler.post(new Runnable() {
                    public void run() {
                        webView.loadUrl("javascript:" + BaseFragment.comTag + ".callBackJs('" + responseBody + "','" + requestID + "')");
                    }
                });
            }

        } catch (PluginNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setService(String service) {
        this.service = service;
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public void setArgs(JSONObject args) {
        this.args = args;
    }

    @Override
    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    @Override
    public void setMessageHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }
}
