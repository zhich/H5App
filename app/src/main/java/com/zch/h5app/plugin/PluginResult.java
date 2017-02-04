package com.zch.h5app.plugin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zch
 * @description IPlugin处理结果
 * @created at 2017/2/4
 */
public class PluginResult {
    static final String TAG = PluginResult.class.getSimpleName();

    public String message;
    private Status status = Status.OK;
    private JSONObject jsonObject;

    public PluginResult(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public PluginResult(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getJSONString() {
        JSONObject jsonObject = new JSONObject();
        String json = "{}";
        try {
            JSONObject m = new JSONObject(message);

            jsonObject.put("message", m);
            jsonObject.put("status", status.ordinal());
            json = jsonObject.toString();
        } catch (JSONException e) {
            try {
                jsonObject.put("message", message);
                jsonObject.put("status", status.ordinal());
                json = jsonObject.toString();
            } catch (JSONException je) {
                e.printStackTrace();
            }
        }
        return json;
    }

    /**
     * 获取异常JSON串
     *
     * @param e
     * @return
     */
    public static String getErrorJSON(Throwable e) {

        String msg = e.getMessage();
        return new PluginResult(msg == null ? "" : msg, Status.ERROR)
                .getJSONString();
    }

    public static PluginResult newEmptyPluginResult() {
        return new PluginResult("");
    }

    public static PluginResult callBackPluginResult() {
        return new PluginResult("0");
    }

    public static PluginResult newErrorPluginResult(String message) {
        return new PluginResult(message, Status.ERROR);
    }

    public static PluginResult newErrorPluginResult(Exception e) {
        return PluginResult.newErrorPluginResult(e.getMessage());
    }

    public static enum Status {

        OK(0), ERROR(1), ILLIGAL(2);

        Status(int i) {

        }
    }
}
