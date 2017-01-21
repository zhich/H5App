package com.zch.h5app.activity;

import android.os.Bundle;

import com.zch.h5app.R;
import com.zch.h5app.plugin.PluginManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPluginManager = new PluginManager(this);
        mPluginManager.loadPlugin();
        mPluginManager.onCreate(savedInstanceState);

        initView();
        loadUrl("main.html", "h5", 0);
    }
}
