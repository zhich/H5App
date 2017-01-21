package com.zch.h5app.plugin;

import org.json.JSONException;
import org.json.JSONObject;

public class App extends Plugin {

    @Override
    public PluginResult exec(String action, JSONObject args)
            throws ActionNotFoundException {
        if ("setTitle".equals(action)) {//设置标题内容
            return setTitle(args);
        } else {
            throw new ActionNotFoundException("App", action);
        }
    }


//    @Override
//    public PluginResult execAsyn(String action, JSONObject args, String requestID) throws ActionNotFoundException {
//        if("checkLoginWithStatus".equals(action)){//
//            return checkLoginWithStatus(args, requestID);
//        }else if("alipay".equals(action)){//支付宝支付
//            return alipay(args,requestID);
//        }else if("weixinPay".equals(action)){//微信支付
//            return weixinPay(args,requestID);
//        }else {
//            throw new ActionNotFoundException("App", action);
//        }
//    }

    private PluginResult setTitle(JSONObject args) {
        try {
            String title = args.getString("title");
            // mContext.setTopTitle(title);
            return PluginResult.newEmptyPluginResult();
        } catch (JSONException e) {
            e.printStackTrace();
            return PluginResult.newErrorPluginResult(e.getMessage());
        }
    }


}
