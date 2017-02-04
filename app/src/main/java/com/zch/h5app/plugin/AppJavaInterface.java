package com.zch.h5app.plugin;

import android.webkit.JavascriptInterface;

import java.util.Hashtable;

/**
 * @author zch
 * @description 与Javascript交互的对象
 * @created at 2017/2/4
 */
public class AppJavaInterface implements java.io.Serializable {

    private static final long serialVersionUID = 1898316400768814976L;
    private static Hashtable<String, String> CMDS = new Hashtable<String, String>();
    private static Hashtable<String, String> ARGS = new Hashtable<String, String>();

    /**
     * 从页面写入参数
     *
     * @param cmds
     * @param id
     */
    @JavascriptInterface
    public void setCmds(String cmds, String id) {
        CMDS.put(id, cmds);
    }

    /**
     * 从页面写入参数
     *
     * @param args
     * @param id
     */
    @JavascriptInterface
    public void setArgs(String args, String id) {
        ARGS.put(id, args);
    }

    /**
     * 只取一次即 清除
     *
     * @param id
     * @return
     */
    public static String getCmdOnce(String id) {
        String result = CMDS.get(id);
        CMDS.remove(id);
        return result;
    }

    /**
     * 只取一次即 清除
     *
     * @param id
     * @return
     */
    public static String getArgOnce(String id) {
        String result = ARGS.get(id);
        ARGS.remove(id);
        return result;
    }
}
