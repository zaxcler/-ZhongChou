package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.IntroduceBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by zaxcler on 15/10/20.
 */
public class ProjectDetailIntroducationActivity  extends Activity{


    private int id;
    private int type;
    private Intent intent;

    private WebView project_introduce;
    private WebView project_member;
    private WebView project_future;
    private WebView project_target;
    private WebView project_competition;
    private WebView project_adavntage;
    private WebView Profit_model;

    private LinearLayout member;
    private LinearLayout future;
    private LinearLayout target;
    private LinearLayout competition;
    private LinearLayout adavntage;
    private LinearLayout model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.project_detail_introduction_activity);
        ZXUtils.initTitle(this, "项目详情", false);
        intent=getIntent();
        id=intent.getIntExtra("id", 0);
        type=intent.getIntExtra("type",0);
        findView();
        init();



    }

    private void findView() {
        project_introduce = (WebView) findViewById(R.id.project_introduce);
        project_member = (WebView) findViewById(R.id.project_member);
        project_future = (WebView) findViewById(R.id.project_future);
        project_target = (WebView) findViewById(R.id.project_target);
        project_competition = (WebView) findViewById(R.id.project_competition);
        project_adavntage = (WebView) findViewById(R.id.project_adavntage);
        Profit_model = (WebView) findViewById(R.id.Profit_model);

        member = (LinearLayout) findViewById(R.id.member);
        future = (LinearLayout) findViewById(R.id.future);
        target = (LinearLayout) findViewById(R.id.target);
        competition = (LinearLayout) findViewById(R.id.competition);
        adavntage = (LinearLayout) findViewById(R.id.adavntage);
        model = (LinearLayout) findViewById(R.id.model);

        switch (type){
            case 0:

                member.setVisibility(View.VISIBLE);
                future.setVisibility(View.VISIBLE);
                target.setVisibility(View.VISIBLE);
                competition.setVisibility(View.VISIBLE);
                adavntage.setVisibility(View.VISIBLE);
                model.setVisibility(View.VISIBLE);

                project_member.setVisibility(View.VISIBLE);
                project_future.setVisibility(View.VISIBLE);
                project_target.setVisibility(View.VISIBLE);
                project_competition.setVisibility(View.VISIBLE);
                project_adavntage.setVisibility(View.VISIBLE);
                Profit_model.setVisibility(View.VISIBLE);

                break;
            case 1:
            case 2:
                break;

        }

    }

    private void init() {
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        NetworkHepler hepler=new NetworkHepler();
        hepler.put("id",id);
        NetWorkUtils.doGet(BaseInfo.Project_introduce_gq, null, hepler, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("introduce",response.toString());
                if (response.optInt("code") == 1) {
                    if (response.optJSONObject("body") != null) {
                        IntroduceBean introduceBean = JsonUtils.getResult(response.optJSONObject("body"), IntroduceBean.class);
                        Log.e("introduceBean",introduceBean.toString());
                        if (introduceBean!=null){
                            bindData(introduceBean);
                        }

                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }

    private void bindData(IntroduceBean introduceBean) {

            try {
                if (introduceBean.getDescription()!=null && !"".equals(introduceBean.getDescription())){
                project_introduce.getSettings().setDefaultTextEncodingName("UTF-8");
//                project_introduce.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    /**
                     * 设置页面适应屏幕
                     */
                project_introduce.getSettings().setUseWideViewPort(true);
                project_introduce.getSettings().setLoadWithOverviewMode(true);

//                project_introduce.loadData(URLEncoder.encode(introduceBean.getDescription(), "utf-8"), "text/html;charset=UTF-8", null);
                project_introduce.loadDataWithBaseURL(null,introduceBean.getDescription(),"text/html","utf-8",null);
                }
                if (introduceBean.getTeams()!=null && !"".equals(introduceBean.getTeams())) {
                    project_member.getSettings().setDefaultTextEncodingName("UTF-8");
//                    project_member.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//                    project_member.loadData(URLEncoder.encode(introduceBean.getTeams(), "utf-8"), "text/html;charset=UTF-8", null);
                    project_member.getSettings().setUseWideViewPort(true);
                    project_member.getSettings().setLoadWithOverviewMode(true);
                    project_member.loadDataWithBaseURL(null, introduceBean.getTeams(), "text/html", "utf-8", null);
                }
                if (introduceBean.getFuture()!=null && !"".equals(introduceBean.getFuture())) {
                    project_future.getSettings().setDefaultTextEncodingName("UTF-8");
//                    project_future.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    project_future.getSettings().setUseWideViewPort(true);
                    project_future.getSettings().setLoadWithOverviewMode(true);
//                    project_future.loadData(URLEncoder.encode(introduceBean.getFuture(), "utf-8"), "text/html;charset=UTF-8", null);
                    project_future.loadDataWithBaseURL(null, introduceBean.getFuture(), "text/html", "utf-8", null);
                }
                if (introduceBean.getCustomer()!=null && !"".equals(introduceBean.getCustomer())){
                    project_target.getSettings().setDefaultTextEncodingName("UTF-8");
//                    project_target.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    project_target.getSettings().setUseWideViewPort(true);
                    project_target.getSettings().setLoadWithOverviewMode(true);
                    project_target.loadDataWithBaseURL(null, introduceBean.getCustomer(), "text/html", "utf-8", null);
//                    project_target.loadData(URLEncoder.encode(introduceBean.getCustomer(), "utf-8"), "text/html;charset=UTF-8", null);
                }

                if(introduceBean.getCompete()!=null&&!"".equals(introduceBean.getCompete())) {
                    project_competition.getSettings().setDefaultTextEncodingName("UTF-8");
//                    project_competition.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    project_competition.getSettings().setUseWideViewPort(true);
                    project_competition.getSettings().setLoadWithOverviewMode(true);
                    project_competition.loadDataWithBaseURL(null, introduceBean.getCompete(), "text/html", "utf-8", null);
//                    project_competition.loadData(URLEncoder.encode(introduceBean.getCompete(), "utf-8"), "text/html;charset=UTF-8", null);
                }

                if (introduceBean.getGoodness()!=null&&!"".equals(introduceBean.getGoodness())) {
                    project_adavntage.getSettings().setDefaultTextEncodingName("UTF-8");
//                    project_adavntage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    project_adavntage.getSettings().setUseWideViewPort(true);
                    project_adavntage.getSettings().setLoadWithOverviewMode(true);
                    project_adavntage.loadDataWithBaseURL(null, introduceBean.getGoodness(), "text/html", "utf-8", null);
//                    project_adavntage.loadData(URLEncoder.encode(introduceBean.getGoodness(), "utf-8"), "text/html;charset=UTF-8", null);
                }
                if (introduceBean.getProfit()!=null&&!"".equals(introduceBean.getProfit())){
                    Profit_model.getSettings().setDefaultTextEncodingName("UTF-8");
//                    Profit_model.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    Profit_model.getSettings().setUseWideViewPort(true);
                    Profit_model.getSettings().setLoadWithOverviewMode(true);
                    Profit_model.loadDataWithBaseURL(null, introduceBean.getProfit(), "text/html", "utf-8", null);
//                    Profit_model.loadData(URLEncoder.encode(introduceBean.getProfit(), "utf-8"),"text/html;charset=UTF-8",null);
                }



            } catch (Exception e) {
                e.printStackTrace();
            }


    }
}
