package com.zch.h5app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zch.h5app.R;
import com.zch.h5app.plugin.AppJavaInterface;
import com.zch.h5app.plugin.AsynServiceHandler;
import com.zch.h5app.plugin.AsynServiceHandlerImpl;
import com.zch.h5app.plugin.IPlugin;
import com.zch.h5app.plugin.PluginManager;
import com.zch.h5app.plugin.PluginNotFoundException;
import com.zch.h5app.plugin.PluginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;

import static com.zch.h5app.fragment.BaseFragment.comTag;

public class BaseActivity extends Activity {

    protected RelativeLayout mTopbarRl;//标题栏
    protected TextView mTopbarLeftTv;//左边返回图标
    protected ImageView mTopbarMidIv;//中间图片
    protected TextView mTopbarTv;//中间文字
    protected ImageView mTopbarRightIv;//右侧图片
    protected TextView mTopbarRighIv;//右侧文字

    protected PluginManager mPluginManager;
    protected WebView mWebView = null;
    protected WebServerClient webviewClient;
    protected WebChromeClient webChromeClient;

    protected String mRightType;//用了记录右上角资源的类型
    protected String mTitleType;//用了记录标题资源的类型

    @Override
    public void onStart() {
        if (null != mPluginManager) {
            mPluginManager.onStart();
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mPluginManager) {
            mPluginManager.onResume();
        }
    }

    @Override
    public void onPause() {
        if (null != mPluginManager) {
            mPluginManager.onPause();
        }
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mPluginManager) {
            mPluginManager.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroy() {
        if (null != mPluginManager) {
            mPluginManager.onDestroy();
        }
        if (null != this.mWebView) {
            this.mWebView.destroy();
        }
        super.onDestroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @JavascriptInterface
    protected void initView() {
        mWebView = (WebView) this.findViewById(R.id.main_wv_webView);

        mWebView.requestFocus();
        mWebView.setOnKeyListener(new View.OnKeyListener() { // webview can
            // go back
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //goBackEvent();
                        return true;
                    }
                }
                return false;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webviewClient = new WebServerClient();
        webChromeClient = new WebServerChromeClient();
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webviewClient);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.requestFocusFromTouch();
        mWebView.addJavascriptInterface(new AppJavaInterface(), "ptvAction");


        if (Build.VERSION.SDK_INT >= 16) {
            Class<?> clazz = mWebView.getSettings().getClass();
            Method method;
            try {
                method = clazz.getMethod(
                        "setAllowUniversalAccessFromFileURLs",
                        boolean.class);
                if (method != null) {
                    method.invoke(mWebView.getSettings(), true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // mWebView.loadUrl("file:///android_asset/main.html");
    }

    /**
     * 加载Url
     *
     * @param entryUrl：url相对路径
     * @param localPackage：包名
     * @param h5TypeKey：h5文件资源类型，0：本地；1：web
     */
    public void loadUrl(String entryUrl, String localPackage, int h5TypeKey) {
        localPackage = localPackage + File.separator + "html";

        if (entryUrl == null || entryUrl.length() == 0) {
            entryUrl = "index.html";
        }
        String url = null;
        if (h5TypeKey == 0) {
            url = "file:///android_asset" + File.separator + localPackage + File.separator + entryUrl;
        } else if (h5TypeKey == 1) {
            url = entryUrl;
        }
        mWebView.loadUrl(url);
    }

    /**
     * 异步加载
     *
     * @param responseBody
     * @param requestID
     */
    public void asynLoadUrl(String responseBody, String requestID) {
//        String url = "javascript:" + comTag + ".callBackJs('" + responseBody + "','" + requestID + "')";
//        mWebView.loadUrl(url);
    }

    private class WebServerChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // mProgress.setProgress(newProgress);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {

            JSONObject args = null;
            JSONObject head = null;
            try {
                head = new JSONObject(message);
                if (defaultValue != null && !defaultValue.equals("")
                        && !defaultValue.equals("null")) {
                    try {
                        args = new JSONObject(defaultValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String execResult = mPluginManager.exec(
                        head.getString(IPlugin.SERVICE),
                        head.getString(IPlugin.ACTION), args);
                result.confirm(execResult);
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
                result.confirm(PluginResult.getErrorJSON(e));
                return true;
            } catch (PluginNotFoundException e) {
                e.printStackTrace();
                result.confirm(PluginResult.getErrorJSON(e));
                return true;
            }

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            // mTitleTextView.setText(title == null ? "" : title);
        }

    }

    private class WebServerClient extends WebViewClient {
        @SuppressLint("HandlerLeak")
        Handler myHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //
                }
                super.handleMessage(msg);
            }
        };

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.toUpperCase().startsWith(comTag)) {
                String id = "NONE";
                try {
                    id = url.substring(url.indexOf("id=") + 3);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                JSONObject cmd = null;
                JSONObject arg = null;
                try {
                    cmd = new JSONObject(AppJavaInterface.getCmdOnce(id));
                    arg = new JSONObject(AppJavaInterface.getArgOnce(id));
                } catch (Exception e1) {
                    e1.printStackTrace();
                    return false;
                }
                try {
                    AsynServiceHandler asyn = new AsynServiceHandlerImpl();
                    asyn.setService(cmd.getString("service"));
                    asyn.setAction(cmd.getString("action"));
                    asyn.setArgs(arg);
                    asyn.setWebView(mWebView);
                    asyn.setRequestID(id);
                    asyn.setMessageHandler(myHandler);
                    asyn.setPluginManager(mPluginManager);

                    Thread thread = new Thread(asyn, "asyn_"
                            + id);
                    thread.start();

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

                return true;
            } else if (url.indexOf("tel:") >= 0) {
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // mProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // mProgress.setVisibility(View.GONE);
        }

        @SuppressLint("NewApi")
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();
            int sdk_version = Build.VERSION.SDK_INT;
            if (sdk_version >= 17) {

                super.onReceivedSslError(view, handler, error);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }

}
