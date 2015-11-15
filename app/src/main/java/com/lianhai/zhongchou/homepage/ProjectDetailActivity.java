package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.adapter.AddressAdapter;
import com.lianhai.zhongchou.adapter.CommentAadapter;
import com.lianhai.zhongchou.adapter.GYSupportAdapter;
import com.lianhai.zhongchou.adapter.InvestsPhotoAdapter;
import com.lianhai.zhongchou.adapter.ProjectTJAdapter;
import com.lianhai.zhongchou.bean.CityOrPrivance;
import com.lianhai.zhongchou.bean.CommentBean;
import com.lianhai.zhongchou.bean.CreateUserInfo;
import com.lianhai.zhongchou.bean.LeaderInfo;
import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.bean.ProjectDetailBean;
import com.lianhai.zhongchou.bean.Repay;
import com.lianhai.zhongchou.bean.TestBean;
import com.lianhai.zhongchou.bean.UserInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.CircleImageView;
import com.lianhai.zhongchou.customview.CircleProgressbar;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.customview.MyGridView;
import com.lianhai.zhongchou.customview.MyListview;
import com.lianhai.zhongchou.customview.MyLoadingProgress;
import com.lianhai.zhongchou.customview.MySpinner;
import com.lianhai.zhongchou.database.DataBaseUtils;
import com.lianhai.zhongchou.database.DatabaseHelper;
import com.lianhai.zhongchou.mypage.UserZoneActivity;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.lianhai.zhongchou.utils.TimeCounter;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zaxcler on 15/10/16.
 */
public class ProjectDetailActivity extends Activity implements View.OnClickListener{
    private MyListview support_list;
    private MyListview comment_list;
    private MyGridView recommend_list;
    private LinearLayout confirm;
    private TextView check_detail;
    private Intent intent;
    private int type;
    private String URL;
    public int id;
    public CommentAadapter adapter;
    private LinearLayout introduct_gq;
    private LinearLayout introduct_gy_xf;

    private Button comment;
    private TextView collection;//点击收藏
    private TextView liulan;//浏览数量
    private ImageView logo;//封面图

    private SwipeRefreshLayout swipleReflashLayout;//刷新控件
    private ScrollView scrollview;


    //---发起人部分---
    private LinearLayout sponsor_layout;
    private CircleImageView sponsor_img;
    private TextView sponsor_name;
    //---领投人部分---
    private LinearLayout leader_layout;
    private CircleImageView leader_img;
    private TextView leader_name;

    private LinearLayout sponsor_list_layout;//投资人
    private TextView nums;//投资人数量




    private ProjectDetailBean projectDetailBean;
    private GridView invests_photo;//投资人头像






    //-------下面属于股权的页面--------
    private TextView gq_title;
    private TextView gq_content;
    private TextView gq_province;
    private TextView gq_city;
    private TextView gq_target;
    private TextView gq_current_money;
    private TextView gq_time;
    private CircleProgressbar progressbar;
    private TextView gq_churang;
    private TextView gq_guzhi;
    private TextView gq_fenlei;
    private View dashed_line;//虚线
    private LinearLayout gq_huibao;
    private TextView special_power;
    private TextView dongtai;
    private TextView confirm_text;
    private LinearLayout bottom;//底部菜单

    //-------下面属于公益和消费---------
    private TextView gy_xf_title;
    private TextView gy_xf_content;
    private TextView gy_xf_target;
    private TextView gy_xf_current_money;
    private TextView gy_xf_time;
    private TextView gy_xf_fenlei;
    





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.project_detail_activity);
        ZXUtils.initTitle(this, "项目详情", false);
        intent=getIntent();
        type=intent.getIntExtra("type",0);
        id=intent.getIntExtra("id",0);

        findView();
        init();
    }

    private void findView() {

        support_list = (MyListview) findViewById(R.id.support_list);
        comment_list = (MyListview) findViewById(R.id.comment_list);
        recommend_list = (MyGridView) findViewById(R.id.recommend_list);
        confirm = (LinearLayout) findViewById(R.id.confirm);
        check_detail = (TextView) findViewById(R.id.check_detail);

        //-----通用部分---------
        logo = (ImageView) findViewById(R.id.logo);
        collection = (TextView) findViewById(R.id.collection);
        liulan = (TextView) findViewById(R.id.liulan);
        nums = (TextView) findViewById(R.id.nums);
        comment = (Button) findViewById(R.id.comment);

        invests_photo = (GridView) findViewById(R.id.invests_photo);


        introduct_gq = (LinearLayout) findViewById(R.id.introduct_gq);
        introduct_gy_xf = (LinearLayout) findViewById(R.id.introduct_gy_xf);

        //------发起人部分------
        sponsor_layout = (LinearLayout) findViewById(R.id.sponsor_layout);
        sponsor_img = (CircleImageView) findViewById(R.id.sponsor_img);
        sponsor_name = (TextView) findViewById(R.id.sponsor_name);

        //------发起人部分------
        leader_layout = (LinearLayout) findViewById(R.id.leader_layout);
        leader_img = (CircleImageView) findViewById(R.id.leader_img);
        leader_name = (TextView) findViewById(R.id.leader_name);



        //-------股权内容---------
        gq_title = (TextView) findViewById(R.id.gq_title);
        gq_content = (TextView) findViewById(R.id.gq_content);
        gq_province = (TextView) findViewById(R.id.gq_province);
        gq_city = (TextView) findViewById(R.id.gq_city);
        gq_target = (TextView) findViewById(R.id.gq_target);
        gq_current_money = (TextView) findViewById(R.id.gq_current_money);
        gq_time = (TextView) findViewById(R.id.gq_time);
        progressbar = (CircleProgressbar) findViewById(R.id.progressbar);
        gq_churang = (TextView) findViewById(R.id.gq_churang);
        gq_guzhi = (TextView) findViewById(R.id.gq_guzhi);
        gq_fenlei = (TextView) findViewById(R.id.gq_fenlei);
        dongtai = (TextView) findViewById(R.id.dongtai);
        confirm_text = (TextView) findViewById(R.id.confirm_text);

        gq_huibao = (LinearLayout) findViewById(R.id.gq_huibao);
        special_power = (TextView) findViewById(R.id.special_power);
        dashed_line = findViewById(R.id.dashed_line);
        bottom = (LinearLayout) findViewById(R.id.bottom);

        //-------公益消费内容---------
        gy_xf_title = (TextView) findViewById(R.id.gy_xf_title);
        gy_xf_content = (TextView) findViewById(R.id.gy_xf_content);
        gy_xf_target = (TextView) findViewById(R.id.gy_xf_target);
        gy_xf_current_money = (TextView) findViewById(R.id.gy_xf_current_money);
        gy_xf_time = (TextView) findViewById(R.id.gy_xf_time);
        gy_xf_fenlei = (TextView) findViewById(R.id.gy_xf_fenlei);

        sponsor_list_layout = (LinearLayout) findViewById(R.id.sponsor_list_layout);

        swipleReflashLayout = (SwipeRefreshLayout) findViewById(R.id.swipleReflashLayout);
        scrollview = (ScrollView) findViewById(R.id.scrollview);





    }

    private void init() {

        swipleReflashLayout.requestChildFocus(scrollview,scrollview);
        swipleReflashLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                loadComments();
                loadProject();
            }
        });

        switch (type){
            case 0:
                introduct_gy_xf.setVisibility(View.GONE);
                introduct_gq.setVisibility(View.VISIBLE);
                bottom.setVisibility(View.VISIBLE);
                break;
            case 1:
            case 2:
                introduct_gy_xf.setVisibility(View.VISIBLE);
                introduct_gq.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);
                break;
        }
        /**
         * 加载数据
         */
        loadData();
        /**
         * 加载评论
         */
        loadComments();
        /**
         * 加载推荐项目
         */
        loadProject();

        /**
         * 浏览数加一
         */
        view();

        sponsor_list_layout.setOnClickListener(this);
        collection.setOnClickListener(this);
        sponsor_layout.setOnClickListener(this);
        leader_layout.setOnClickListener(this);
        comment.setOnClickListener(this);


        confirm.setOnClickListener(this);
        check_detail.setOnClickListener(this);
    }

    /**
     * 浏览数加1
     */
    private void view() {

        NetworkHepler hepler=new NetworkHepler();
        hepler.put("id",id);
        NetWorkUtils.doGet(BaseInfo.BaseUrl_xu,null,hepler,new JsonHttpResponseHandler());
    }


    /**
     * 加载数据
     */
    private void loadData() {

        MyLoadingProgress.showLoadingDialog(this);
        switch (type){
            case 0:
               URL= BaseInfo.Project_detail;
                break;
            case 1:
            case 2:
                URL= BaseInfo.Project_detail_entity;
                break;

        }
        NetworkHepler hepler=new NetworkHepler();
        hepler.put("id", id);
        NetWorkUtils.doGet(URL, null, hepler, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("response", response.toString());
                if (response.optInt("code") == 1) {
                    if (response.optJSONObject("body") != null) {
                        bindData(response.optJSONObject("body"));
                    }

                } else {
                    Log.e("response", "" + response.optString("result"));
                    Toast.makeText(ProjectDetailActivity.this, response.optString("result"), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("responseString", "" + responseString);
                Toast.makeText(ProjectDetailActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * 绑定数据
     * @param object
     */
    private void bindData(JSONObject object) {
        projectDetailBean= JsonUtils.getResult(object,ProjectDetailBean.class);
        if (projectDetailBean!=null){

            if (projectDetailBean.getLogo()!=null){
                ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu +projectDetailBean.getLogo(),logo, MyApplication.options_image);
            }
            collection.setText(projectDetailBean.getFavorite()+"");

            liulan.setText(projectDetailBean.getView()+"");

            switch (type){
                case 0:
                    bindDataGq(projectDetailBean);
                    break;
                case 1:
                case 2:
                    bindDataGYXF(projectDetailBean);
                    break;
            }

            if (projectDetailBean.getCreate_user_info()!=null){
                CreateUserInfo info=projectDetailBean.getCreate_user_info();
                ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu +info.getUser_img(),sponsor_img,MyApplication.options_image);
                if (info.getUsername()!=null){
                    sponsor_name.setText(info.getUsername());
                }
            }
            if (projectDetailBean.getLeader_info()!=null){
                leader_layout.setVisibility(View.VISIBLE);
                LeaderInfo info=projectDetailBean.getLeader_info();
                ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu +info.getUser_img(),leader_img,MyApplication.options_image);
                if (info.getUsername()!=null){
                    leader_name.setText(info.getUsername());
                }
            }

            nums.setText(projectDetailBean.getInvests() + "");
            if (projectDetailBean.getInvest_info()!=null){
                InvestsPhotoAdapter adapter=new InvestsPhotoAdapter(this,projectDetailBean.getInvest_info(),R.layout.image_layout);
                invests_photo.setAdapter(adapter);
                invests_photo.setNumColumns(projectDetailBean.getInvest_info().size());
            }

        }
        MyLoadingProgress.closeLoadingDialog();
        swipleReflashLayout.setRefreshing(false);
    }
    /**
     * 加载评论
     */
    private void loadComments() {

        NetworkHepler hepler=new NetworkHepler();
        hepler.put("id",id);
        NetWorkUtils.doGet(BaseInfo.Comment_list,null,hepler,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.optInt("code")==1){
                    if (response.optJSONArray("body")!=null){
                        List<CommentBean> list=JsonUtils.getResultList(response.optJSONArray("body"),CommentBean.class);
                        adapter=new CommentAadapter(ProjectDetailActivity.this,list,R.layout.comment_item);
                        comment_list.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    /**
     * 加载推荐项目
     */
    private void loadProject() {
        NetworkHepler params=new NetworkHepler();
        params.put("c",0);//分类
        params.put("s", 0);//阶段
        params.put("h", 0);//排序
        params.put("p", 1);//页数
        params.put("limit",4);
        NetWorkUtils.doGet(BaseInfo.Project_list,null,params,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("response", response.toString());
                if (response.optInt("code")==1){

                    if (response.optJSONArray("body")!=null){
                        final List<ProjectBean> list=JsonUtils.getResultList(response.optJSONArray("body"),ProjectBean.class);
                        if (list!=null){
                            ProjectTJAdapter tjAdapter=new ProjectTJAdapter(ProjectDetailActivity.this,list,R.layout.recommend_item);
                            recommend_list.setAdapter(tjAdapter);
                            recommend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    intent.setClass(ProjectDetailActivity.this, ProjectDetailActivity.class);
                                    intent.putExtra("id",list.get(i).getId());
                                    intent.putExtra("type",list.get(i).getType());
                                    startActivity(intent);
                                }
                            });

                        }

                    }
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response", responseString);
            }
        });

    }


    /**
     * 绑定股权
     * @param projectDetailBean
     */
    public void bindDataGq(ProjectDetailBean projectDetailBean){
        if (projectDetailBean.getName()!=null){
            gq_title.setText(projectDetailBean.getName());
        }
        if (projectDetailBean.getIntroduct()!=null){
            gq_content.setText(projectDetailBean.getIntroduct());
        }
            /*
            城市
             */
//        DatabaseHelper helper=new DatabaseHelper(this);

        String sql="select name from area where id=?";
//        String city_name=helper.query(sql, null);
        String city_name=DataBaseUtils.query(sql, new String[]{""+projectDetailBean.getCity()});
        String province_name=DataBaseUtils.query(sql, new String[]{""+projectDetailBean.getProvince()});

        gq_province.setText(province_name);
        gq_city.setText(city_name);

        /**
         * 股权动态
         */
//        MyApplication.preferences.getString("")
//        dongtai

        double targetMoney= projectDetailBean.getPre_value();
        if (targetMoney>10000){
            gq_target.setText(targetMoney/10000+"万元");
        }else {
            gq_target.setText(targetMoney+"元");
        }

        double currentMoney= projectDetailBean.getTotalMoney();
        if (currentMoney>10000){
            gq_current_money.setText(currentMoney/10000+"万元");
        }else {
            gq_current_money.setText(currentMoney+"元");
        }

        long end_day=Long.valueOf(projectDetailBean.getEnd_at())*1000;
        Date end_date=new Date(end_day);
        Date currentdate=new Date(System.currentTimeMillis());
        int leftday= TimeCounter.countTimeOfDay(currentdate, end_date);
        gq_time.setText(leftday + "天");

        if (projectDetailBean.getPre_value()!=0) {
            if (projectDetailBean.getTotalMoney() / projectDetailBean.getPre_value() > 1) {
                progressbar.setProgress((int)(projectDetailBean.getTotalMoney() / projectDetailBean.getPre_value()*100));
            } else {
                int progress = (int) (projectDetailBean.getTotalMoney() / projectDetailBean.getPre_value() * 100);
                progressbar.setProgress(progress);
            }
        }

        if (projectDetailBean.getValue()!=0){
            DecimalFormat format=new DecimalFormat("##.00");
            double churang=projectDetailBean.getPre_value()/projectDetailBean.getValue()*100;
            String churangbili=format.format(churang)+"%";
            gq_churang.setText("出让股权比例:"+churangbili);
        }

        if (projectDetailBean.getValue()>10000){
            gq_guzhi.setText("项目估值："+projectDetailBean.getValue()/10000+"万元");
        }else {
            gq_guzhi.setText("项目估值："+projectDetailBean.getValue()+"元");
        }

        if (projectDetailBean.getCareer()!=null){
            gq_fenlei.setText("所属分类："+projectDetailBean.getCareer());
        }else{
            gq_fenlei.setText("所属分类：");
        }
        gq_huibao.setVisibility(View.VISIBLE);

        special_power.setText(projectDetailBean.getSpecial_power());
        dashed_line.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        long end_at=Long.valueOf(projectDetailBean.getEnd_at()+"")*1000;
        if (end_at<System.currentTimeMillis())
        {
            confirm_text.setText("已结束");
            confirm.setBackgroundColor(getResources().getColor(R.color.lightlightboro));
        }

    }

    /**
     * 绑定公益和消费
     * @param projectDetailBean
     */
    public void bindDataGYXF(ProjectDetailBean projectDetailBean){
        if (projectDetailBean.getRepays()!=null){
            support_list.setVisibility(View.VISIBLE);
            ArrayList<Repay> repays=projectDetailBean.getRepays();
            GYSupportAdapter adapter=new GYSupportAdapter(this,repays,R.layout.support_item);
            support_list.setAdapter(adapter);
        }

        if (projectDetailBean.getName()!=null){
            gy_xf_title.setText(projectDetailBean.getName());
        }
        if (projectDetailBean.getIntroduct()!=null){
            gy_xf_content.setText(projectDetailBean.getIntroduct());
        }


        double targetMoney= projectDetailBean.getPre_value();
        if (targetMoney>10000){
            gy_xf_target.setText(targetMoney/10000+"万元");
        }else {
            gy_xf_target.setText(targetMoney+"元");
        }

        double currentMoney= projectDetailBean.getTotalMoney();
        if (currentMoney>10000){
            gy_xf_current_money.setText(currentMoney/10000+"万元");
        }else {
            gy_xf_current_money.setText(currentMoney+"元");
        }
        if (projectDetailBean.getCareer()!=null){
            gy_xf_fenlei.setText("所属分类："+projectDetailBean.getCareer());
        }else{
            gy_xf_fenlei.setText("所属分类：");
        }

        long end_day=Long.valueOf(projectDetailBean.getEnd_at())*1000;
        Date end_date=new Date(end_day);
        Date currentdate=new Date(System.currentTimeMillis());
        int leftday= TimeCounter.countTimeOfDay(currentdate, end_date);
        gy_xf_time.setText(leftday + "天");



    }
    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.confirm:

                long end_at=Long.valueOf(projectDetailBean.getEnd_at()+"")*1000;
                if (end_at<System.currentTimeMillis())
                {
                    return;
                }
                intent.putExtra("id",id);
                intent.setClass(this,SupportBuyGQActivity.class);
                intent.putExtra("projectDetailBean", projectDetailBean);
                startActivity(intent);
                break;
            case R.id.check_detail:

                if (isCanCheckDetail()){
                    intent.putExtra("id",projectDetailBean.getId());

                    switch (type){
                        case 0:
                            intent.putExtra("url",BaseInfo.Project_introduce_gq);
                            break;
                        case 1:
                        case 2:
                            break;
                    }
                    intent.setClass(this, ProjectDetailIntroducationActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.sponsor_layout:
                intent.putExtra("sid",projectDetailBean.getCreate_user());
                intent.setClass(this, UserZoneActivity.class);
                startActivity(intent);
                break;
            case R.id.leader_layout:
                intent.putExtra("sid",projectDetailBean.getLeader());
                intent.setClass(this, UserZoneActivity.class);
                startActivity(intent);
                break;
            case R.id.comment:
                DialogManager.showCommentReplyDialog(this, null, id);
                break;
            case R.id.collection:
                collection(id);

                break;
            case R.id.sponsor_list_layout:
                intent.setClass(this,SupportListActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
        }
    }

    /**
     * 收藏项目
     * @param id
     */
    private void collection(int id) {
        NetworkHepler hepler=new NetworkHepler();
        hepler.put("id",id);
        NetWorkUtils.doGet(BaseInfo.collection,null,hepler,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("response",response.toString());
                if (response.optInt("code")==1){
                    if (response.optInt("body")==1){
                        collection.setText((projectDetailBean.getFavorite()+1)+"");
                    }else{
                        collection.setText(projectDetailBean.getFavorite()+"");
                    }


                }

                if (response.optString("result")!=null){
                    Toast.makeText(ProjectDetailActivity.this,response.optString("result"),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    /**
     * 是否有权限查看详情
     */
    public boolean isCanCheckDetail(){
        boolean flag=false;

        AlertDialog.Builder builder=new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        String notice="";

        switch (MyApplication.preferences.getInt("Auth_status",0)){
            case 0:
                notice="未申请实名认证";
                flag=false;
                break;
            case 1:
                notice="通过";
                flag=true;
                break;
            case 2:
                notice="实名认证审核中";
                flag=false;

                break;
        }
        builder.setMessage(notice);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wlp=dialog.getWindow().getAttributes();
        wlp.alpha=0.9f;
        dialog.getWindow().setAttributes(wlp);
        if (!flag){
            dialog.show();
        }

        return flag;
    }

}
