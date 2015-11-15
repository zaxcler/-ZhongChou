package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.InvestsItemAdapter;
import com.lianhai.zhongchou.bean.InvestsItemInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zaxcler on 15/11/5.
 */
public class SupportListActivity extends Activity{

    private ListView listview;

    private int id;
    private Intent intent;
    private List<InvestsItemInfo> infos ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.address_or_card_list_activity);
        ZXUtils.initTitle(this, "投资人列表", false);
        intent=getIntent();
        id=intent.getIntExtra("id",0);
        findView();
        init();
    }


    private void findView() {
        listview = (ListView) findViewById(R.id.listview);

    }

    private void init() {

        loadData();

    }

    private void loadData() {

        NetworkHepler hepler=new NetworkHepler();
        hepler.put("id",id);

        NetWorkUtils.doGet(BaseInfo.Invests_list, null, hepler, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("response", response.toString());
                if (response.optInt("code") == 1) {

                    if (response.optJSONArray("body") != null) {
                       infos = JsonUtils.getResultList(response.optJSONArray("body"), InvestsItemInfo.class);
                        if (infos != null) {
                            bindData(infos);
                        }
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("responseString", responseString);
            }
        });

    }

    /**
     * 绑定数据
     */
    private void bindData( List<InvestsItemInfo> infos ) {
        InvestsItemAdapter adapter=new InvestsItemAdapter(this,infos,R.layout.support_member_item);
        listview.setAdapter(adapter);
    }


}
