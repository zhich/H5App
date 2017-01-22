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

}
