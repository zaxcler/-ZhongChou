package com.lianhai.zhongchou.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.AddressBean;
import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.bean.Repay;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.mypage.AddressListActivity;
import com.lianhai.zhongchou.utils.JsonUtils;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.lianhai.zhongchou.utils.ZXUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by zaxcler on 15/10/20.
 */
public class SupportBuyGYXFActivity extends Activity implements View.OnClickListener{
    private TextView name;//名称
    private ImageView project_photo;//项目图片
    private TextView sponsor;//发起人
    private TextView money;//支持金额
    private TextView send_way;//快递方式
    private TextView delete;//减
    private TextView nums;//总价格
    private TextView add;//加
    private TextView chose_address;//选择收货地址
    private TextView SHR;//收货人
    private TextView address;//收货地址
    private TextView repay_num;//回报人数和限制人数
    private TextView content;//内容
    private EditText beizhu;//备注
    private Button confirm;//立即支付


    private Intent intent;
    private Repay repay;
    private int id;//项目id
    private int gid;//商品id

    private double base_money;//基本
    private int count;//购买个数

    private final int CHOOSE_ADDRESS=1;//请求选择地址
    private  AddressBean addressBean;//地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.support_buy_gy_xf_activity);
        ZXUtils.initTitle(this, "标题", false);
        intent=getIntent();
        id=intent.getIntExtra("id", 0);
        repay=intent.getParcelableExtra("repay");
        if (repay!=null){
            gid=repay.getId();
        }
        findView();
        init();

    }


    private void findView() {
        name = (TextView) findViewById(R.id.name);
        project_photo = (ImageView) findViewById(R.id.project_photo);
        sponsor = (TextView) findViewById(R.id.sponsor);
        money = (TextView) findViewById(R.id.money);
        send_way = (TextView) findViewById(R.id.send_way);
        delete = (TextView) findViewById(R.id.delete);
        nums = (TextView) findViewById(R.id.nums);
        delete = (TextView) findViewById(R.id.delete);
        add = (TextView) findViewById(R.id.add);
        chose_address = (TextView) findViewById(R.id.chose_address);
        SHR = (TextView) findViewById(R.id.SHR);
        address = (TextView) findViewById(R.id.address);
        repay_num = (TextView) findViewById(R.id.repay_num);
        content = (TextView) findViewById(R.id.content);
        beizhu = (EditText) findViewById(R.id.beizhu);
        confirm = (Button) findViewById(R.id.confirm);


    }

    private void init() {
        loadData();
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        confirm.setOnClickListener(this);
        chose_address.setOnClickListener(this);



    }

    private void loadData() {
        NetworkHepler hepler=new NetworkHepler();
        hepler.put("id",id);
        hepler.put("gid",gid);

        NetWorkUtils.doGet(BaseInfo.EntityDetail, null, hepler, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("response", response.toString());
                if (response.optInt("code") == 1) {
                    if (response.optJSONObject("body") != null) {
                        bindData(response.optJSONObject("body"));
                    }
                }else {
                    if (response.optString("result")!=null){
                        DialogManager.showNotice(SupportBuyGYXFActivity.this,response.optString("result"));
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("responseString", responseString.toString());
            }
        });
    }

    /**
     * 绑定数据
     * @param
     */
    private void bindData(JSONObject object) {

        if (object.optJSONObject("project")!=null) {


            ProjectBean projectBean = JsonUtils.getResult(object.optJSONObject("project"), ProjectBean.class);
            if (projectBean == null) {
                return;
            }

            if (projectBean.getName() != null) {
                name.setText(projectBean.getName());
            } else {
                name.setText("");
            }
            if (projectBean.getRealname() != null) {
                sponsor.setText("发起人：" + projectBean.getRealname());
            } else {
                sponsor.setText("发起人：");
            }
        }

        if (object.optJSONObject("repay")!=null) {
            Repay repay=JsonUtils.getResult(object.optJSONObject("repay"),Repay.class);
        if (repay==null){
            return;
        }
            Log.e("repay",repay.toString());
            ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu+repay.getImg(),project_photo, MyApplication.options_image);
            Log.e("BaseInfo.BaseUrl_xu",BaseInfo.BaseUrl_xu+repay.getImg());
        base_money=repay.getMoney();
        money.setText("支持金额："+repay.getMoney()+"元");
        if (repay.getShip_money()==0){
            send_way.setText("配送费用：包邮");
        }else {
            send_way.setText("配送费用："+repay.getShip_money()+"元");
        }

        if (repay.getContent()!=null){
            content.setText(repay.getContent());
        }else {
            content.setText("");
        }
        repay_num.setText(object.optInt("applyNum")+"人支持（限"+repay.getNum()+"人)");
        }



        if (object.optJSONObject("addr")!=null){
            addressBean=JsonUtils.getResult(object.optJSONObject("addr"),AddressBean.class);
            if (addressBean==null){
                return;
            }
            if (addressBean.getName()!=null){
                SHR.setText("收货人："+addressBean.getName());
            }else {
                SHR.setText("收货人：");
            }
            if(addressBean.getAddress()!=null){
                address.setText("地址："+addressBean.getAddress());
            }else {
                address.setText("地址：");
            }

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

        Log.e("base_money", "" + base_money);
        nums.setText(count * base_money + "");

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
            case R.id.confirm:
                RequestParams params=new RequestParams();
                if (count==0){
                    DialogManager.showNotice(this,"请选择物品");
                    return;
                }

                params.put("id",id);
                params.put("gid",gid);
                params.put("number",count);
                params.put("remark",beizhu.getText().toString().trim());
                if (repay.getType()==1){
                    if (addressBean==null) {
                        DialogManager.showNotice(this, "请选择收货地址");
                        return;
                    }else {
                        params.put("aid",addressBean.getId());
                    }
                }

                NetWorkUtils.doPost(BaseInfo.EntityOrder,params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("response", response.toString());
                        if (response.optInt("code")==1){
                            intent.setClass(SupportBuyGYXFActivity.this,PayActivity.class);
                            startActivity(intent);
                            SupportBuyGYXFActivity.this.finish();
                        }else{
                            if (response.optString("result")!=null){
                                DialogManager.showNotice(SupportBuyGYXFActivity.this,response.optString("result"));
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("responseString",responseString);
                    }
                });
                break;
            case R.id.chose_address:
                intent.setClass(this, AddressListActivity.class);
                intent.putExtra("chooseaddress", true);
                startActivityForResult(intent, CHOOSE_ADDRESS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            Log.e("responseString","requestCode"+requestCode+"   resultCode"+resultCode+"  data"+data.toString());
            switch (requestCode){
                case CHOOSE_ADDRESS:
                    addressBean=data.getParcelableExtra("addressBean");
                    if (addressBean==null){
                        return;
                    }
                    if (addressBean.getName()!=null){
                        SHR.setText("收货人："+addressBean.getName());
                    }else {
                        SHR.setText("收货人：");
                    }
                    if(addressBean.getAddress()!=null){
                        address.setText("地址："+addressBean.getAddress());
                    }else {
                        address.setText("地址：");
                    }
                    break;
            }
        }

    }
}
