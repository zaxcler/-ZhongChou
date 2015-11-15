package com.lianhai.zhongchou;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.lianhai.zhongchou.utils.ZXUtils;

/**
 * Created by zaxcler on 15/10/16.
 */
public class SponsorFragment extends Fragment {
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sponsor,null);

        findView(view);

        init();
        return view;
    }

    private void findView(View view) {
        webView = (WebView) view.findViewById(R.id.webView);

    }

    private void init() {
        webView.loadUrl("file:///android_asset/create_proj.html");
    }
}
