package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.JionStockBean;
import com.lianhai.zhongchou.bean.ProjectDetailBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by zaxcler on 15/10/20.
 */
public class SupportBuyGQActivity extends Activity implements View.OnClickListener{

    private Button submit;
    private ProjectDetailBean projectDetailBean;
    private Intent intent;

    private TextView name;//名字
    private TextView target_money;//目标额
    private TextView percent;//出让比例
    private TextView min_money;//最小金额
    private TextView delete;//减
    private TextView add;//加
    private TextView nums;//数量
    private TextView my_percent;//投资所占百分比
    private TextView tobeleader;//申请领投
    private TextView leader_role;//领投规则
    private EditText support_reason;//投资理由
    private TextView leader_tv;//领投人寄语
    private EditText leader_msg;//领投人寄语编辑窗口

    private double base_money;//基数
    private int count=0;//购买的数量
    private boolean isLeader=false;//是否领投

    private int leader_type;// 0：不申请 1：申请
    private JionStockBean bean;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.support_buy_gq_activity);
        intent=getIntent();
        projectDetailBean=intent.getParcelableExtra("projectDetailBean");
        MyApplication.addActivityToMap(this,"buygq");
        if (projectDetailBean!=null){
            base_money=projectDetailBean.getMin_value();
            Log.e("base_money",base_money+"");
           if (projectDetailBean.getName()!=null) {
               ZXUtils.initTitle(this, projectDetailBean.getName(), false);
           }
        }else {
            ZXUtils.initTitle(this, "", false);
        }

        findView();
        init();

    }

    private void findView() {
        submit = (Button) findViewById(R.id.submit);
        name = (TextView) findViewById(R.id.name);
        target_money = (TextView) findViewById(R.id.target_money);
        percent = (TextView) findViewById(R.id.percent);
        min_money = (TextView) findViewById(R.id.min_money);
        delete = (TextView) findViewById(R.id.delete);
        add = (TextView) findViewById(R.id.add);
        nums = (TextView) findViewById(R.id.nums);
        my_percent = (TextView) findViewById(R.id.my_percent);
        tobeleader = (TextView) findViewById(R.id.tobeleader);
        leader_role = (TextView) findViewById(R.id.leader_role);
        leader_tv = (TextView) findViewById(R.id.leader_tv);
        support_reason = (EditText) findViewById(R.id.support_reason);
        leader_msg = (EditText) findViewById(R.id.leader_msg);

    }

    private void init() {
        if (projectDetailBean!=null){
            bindData();
        }


        submit.setOnClickListener(this);
        delete.setOnClickListener(this);
        add.setOnClickListener(this);
        leader_role.setOnClickListener(this);
        tobeleader.setOnClickListener(this);

    }

    /**
     * 绑定数据
     */
    private void bindData() {

        if (projectDetailBean!=null){
            if (projectDetailBean.getName()!=null){
                name.setText(projectDetailBean.getName());
            }
            if (projectDetailBean.getPre_value()>10000){
                target_money.setText(projectDetailBean.getPre_value()/10000+"万元");
            }else {
                target_money.setText(projectDetailBean.getPre_value()+"元");
            }

            if (projectDetailBean.getValue()>0){

                DecimalFormat format=new DecimalFormat("##.##");
                percent.setText(format.format(projectDetailBean.getPre_value()/projectDetailBean.getValue()*100)+"%");
            }
            min_money.setText(projectDetailBean.getMin_value() + "元");

            if (projectDetailBean.getLeader_info()==null){
                leader_tv.setVisibility(View.VISIBLE);
                leader_msg.setVisibility(View.VISIBLE);
                tobeleader.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                count--;
                countMoney(count);
                break;
            case R.id.add:
                count++;
                countMoney(count);
                break;
            case R.id.tobeleader:
                toBeLeader();
                break;
            case R.id.submit:
                double money=count*base_money;
                String reason=support_reason.getText().toString().trim();
                String remark=leader_msg.getText().toString().trim();
                if (money<=0){
                    Toast.makeText(this,"投资金额不能为0",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (reason==null || "".equals(reason)){
                    Toast.makeText(this,"投资原因不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                bean =new JionStockBean();
                if (projectDetailBean!=null){
                    bean.setId(projectDetailBean.getId());
                }
                bean.setLeader_type(leader_type);
                bean.setMoney(money);
                bean.setReason(reason);
                if (leader_type==1){
                    if (remark==null || "".equals(remark)){
                        Toast.makeText(this,"领头人寄语不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    bean.setRemark(remark);
                }

                if (bean!=null){
                    RequestParams params=new RequestParams();
                    params.put("id",bean.getId());
                    params.put("money",bean.getMoney());
                    params.put("leader_type",bean.getLeader_type());
                    params.put("reason",bean.getReason());
                    params.put("remark",bean.getRemark());

                    NetWorkUtils.doPost(BaseInfo.Join_Stock, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.e("response", response.toString());
                            if (response.optInt("code") == 1) {
                                intent.putExtra("bean",bean);
                                intent.setClass(SupportBuyGQActivity.this, AboutGQActivity.class);
                                startActivity(intent);
                                SupportBuyGQActivity.this.finish();
                            } else {
                                if (response.optString("result") != null)
                                    DialogManager.showNotice(SupportBuyGQActivity.this, response.optString("result"));
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.e("responseString", responseString);
                        }
                    });
                }

                break;

            case R.id.support_reason:
                break;
        }
    }

    /**
     * 计算钱和百分比
     */
    public void countMoney(int count){

        Log.e("count",""+count);
        //当全局传入的值小于0时，将全局变量count归0
        if (count<0){
            this.count=0;
            return;
        }

        base_money=projectDetailBean.getMin_value();
        Log.e("base_money", "" + base_money);
        nums.setText(count * base_money + "");
        DecimalFormat format=new DecimalFormat("##.##");
        if (projectDetailBean!=null && projectDetailBean.getPre_value()!=0){
            my_percent.setText(format.format(count*base_money/projectDetailBean.getPre_value()*100)+"%");
        }
    }


    /**
     * 申请领投
     */
    public void toBeLeader(){
        if (isLeader){
            isLeader=false;
            tobeleader.setBackgroundResource(R.drawable.stroke_white_soild);
            leader_type=0;
            leader_tv.setVisibility(View.GONE);
            leader_msg.setVisibility(View.GONE);
            tobeleader.setText("申请领投");
            Log.e("isLeader",isLeader+"");

        }else{
            isLeader=true;
            tobeleader.setBackgroundColor(getResources().getColor(R.color.liangyellow));
            leader_type=1;
            leader_tv.setVisibility(View.VISIBLE);
            leader_msg.setVisibility(View.VISIBLE);
            tobeleader.setText("取消领投");
            Log.e("isLeader", isLeader + "");
        }


    }



}
