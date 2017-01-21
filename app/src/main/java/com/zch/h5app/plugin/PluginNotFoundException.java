package com.zch.h5app.plugin;

/**
 * @author JW.Lee
 * @ClassName: PluginNotFoundException
 * @Description: 插件未找到异常，插件配置plugins.xml没有配置插件导致的错误
 * @date 2014年12月10日 下午5:44:17
 */
public class PluginNotFoundException extends Throwable {

    private String pluginName;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PluginNotFoundException(String pluginName) {
        super();
        this.pluginName = pluginName;
    }

    @Override
    public String getMessage() {
        return "Plugin " + pluginName + "未找到,请检查plugins.xml是否配置";
    }
}
