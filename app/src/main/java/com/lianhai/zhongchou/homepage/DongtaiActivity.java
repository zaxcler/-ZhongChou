package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.utils.ZXUtils;

/**
 * Created by zaxcler on 15/11/12.
 */
public class DongtaiActivity extends Activity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dongtai_activity);
        ZXUtils.initTitle(this, "动态", false);
        listView= (ListView) findViewById(R.id.listView);
        init();

    }



    private void init() {

    }


}
