package com.zch.h5library.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zch.h5library.R;
import com.zch.h5library.base.BaseFragment;
import com.zch.h5library.plugin.PluginManager;

/**
 * 首页
 *
 * @author zch 2016-10-10
 */
public class HomeFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPluginManager = new PluginManager(this);
        mPluginManager.loadPlugin();
        mPluginManager.onCreate(savedInstanceState);

        initView();
        loadUrl("home.html", "h5", 0);
    }

}
