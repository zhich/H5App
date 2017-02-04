package com.zch.h5app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zch.h5app.R;
import com.zch.h5app.common.Constant;
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

public class BaseActivity extends Activity {

    protected TextView mBackTv;//返回键
    protected TextView mTitleTv;//标题

    protected PluginManager mPluginManager;
    protected WebView mWebView;
    protected WebServerClient mWebviewClient;
    protected WebChromeClient mWebChromeClient;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    Bundle bundle = msg.getData();
                    String requestID = bundle.getString("requestID");
                    String responseBody = bundle.getString("responseBody");

                    JSONObject jsonObject = new JSONObject();
                    PluginResult pluginResult = null;
                    try {
                        jsonObject.put("responseBody", responseBody);
                        pluginResult = new PluginResult(jsonObject.toString());
                        asynLoadUrl(pluginResult.getJSONString(), requestID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

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
        mBackTv = (TextView) this.findViewById(R.id.toppanel_tv_back);
        mTitleTv = (TextView) this.findViewById(R.id.toppanel_tv_title);

        mWebView = (WebView) this.findViewById(R.id.main_wv_webView);

        mWebView.requestFocus();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        mWebviewClient = new WebServerClient();
        mWebChromeClient = new WebServerChromeClient();
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(mWebviewClient);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.requestFocusFromTouch();
        mWebView.addJavascriptInterface(new AppJavaInterface(), "h5Action");


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
    }

    /**
     * 加载Url
     *
     * @param entryUrl：url相对路径
     * @param localPackage：包名
     * @param h5TypeKey：h5文件资源类型，0：本地；1：web
     */
    public void loadUrl(String entryUrl, String localPackage, int h5TypeKey) {
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
        String url = "javascript:" + Constant.COM_TAG + ".callBackJs('" + responseBody + "','" + requestID + "')";
        mWebView.loadUrl(url);
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
            if (url != null && url.toUpperCase().startsWith(Constant.COM_TAG.toUpperCase())) {
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

                    Thread thread = new Thread(asyn, "asyn_" + id);
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

    /*************************同步请求*************************/

    /**
     * 是否显示返回箭头
     *
     * @param isShow
     */
    public void isShowBack(boolean isShow) {
        if (isShow) {
            mBackTv.setVisibility(View.VISIBLE);
        } else {
            mBackTv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题文字
     *
     * @param title
     */
    public void setTitle(final String title) {
        mTitleTv.setText(null == title ? "" : title);
    }

    /*************************异步请求*************************/

    /**
     * call alipay sdk pay. 调用SDK支付
     *
     * @param productName     商品名称
     * @param productDescribe 商品描述
     * @param productOrder    商品订单
     * @param money           商品价钱
     * @param payType         支付产品类型
     */
    public void alipay(String productName, String productDescribe, String productOrder, final String money, String payType, final String requestID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String responseBody = "成功支付了" + money + "元";
                Message msg = new Message();
                msg.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("requestID", requestID);
                bundle.putString("responseBody", responseBody);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        }).start();
    }

}
