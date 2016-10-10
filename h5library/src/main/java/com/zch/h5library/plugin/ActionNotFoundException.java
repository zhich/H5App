package com.zch.h5library.plugin;

public class ActionNotFoundException extends Throwable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String actionName;
    private String serviceName;


    public ActionNotFoundException(String serivceName, String actionName) {
        super();
        this.serviceName = serivceName;
        this.actionName = actionName;
    }


    @Override
    public String getMessage() {
        return serviceName + " 中的  " + actionName + " action未找到 ,请检查serviceName是否支持";
    }

}
