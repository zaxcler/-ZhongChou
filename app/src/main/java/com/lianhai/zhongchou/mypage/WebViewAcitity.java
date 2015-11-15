package com.lianhai.zhongchou.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.utils.ZXUtils;

/**
 * Created by zaxcler on 15/11/11.
 */
public class WebViewAcitity extends Activity{
    private WebView webview;
    private Intent intent;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.webview_activity);

        intent=getIntent();
        String name=intent.getStringExtra("name");
        ZXUtils.initTitle(this, name, false);

        url=intent.getStringExtra("url");
        webview= (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (url!=null){
            webview.loadUrl(url);
        }

//        webview.loadUrl("http://www.baidu.com");
    }
}
