package com.zch.h5app.plugin;

import org.json.JSONException;
import org.json.JSONObject;

public class App extends Plugin {

    @Override
    public PluginResult exec(String action, JSONObject args)
            throws ActionNotFoundException {
        if ("isShowBack".equals(action)) {//是否显示返回箭头
            return isShowBack(args);
        } else if ("setTitle".equals(action)) {//设置标题
            return setTitle(args);
        } else {
            throw new ActionNotFoundException("App", action);
        }
    }

    @Override
    public PluginResult execAsyn(String action, JSONObject args, String requestID) throws ActionNotFoundException {
        if ("alipay".equals(action)) {//支付宝支付
            return alipay(args, requestID);
        } else {
            throw new ActionNotFoundException("App", action);
        }
    }

    /*************************同步请求*************************/

    private PluginResult isShowBack(JSONObject args) {
        try {
            boolean isShow = args.getBoolean("isShow");
            mContext.isShowBack(isShow);
            return PluginResult.newEmptyPluginResult();
        } catch (JSONException e) {
            e.printStackTrace();
            return PluginResult.newErrorPluginResult(e.getMessage());
        }
    }

    private PluginResult setTitle(JSONObject args) {
        try {
            String title = args.getString("title");
            mContext.setTitle(title);
            return PluginResult.newEmptyPluginResult();
        } catch (JSONException e) {
            e.printStackTrace();
            return PluginResult.newErrorPluginResult(e.getMessage());
        }
    }

    /*************************异步请求*************************/

    private PluginResult alipay(JSONObject args,String requestID){
        try {
            mContext.alipay(args.getString("productName"), args.getString("productDescribe"), args.getString("productOrder"), args.getString("money"), args.getString("payType"), requestID);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return PluginResult.newErrorPluginResult(e);
        }
    }

}
