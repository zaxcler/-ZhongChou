package com.lianhai.zhongchou.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.UserInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.CircleImageView;
import com.lianhai.zhongchou.mypage.UserZoneActivity;
import com.lianhai.zhongchou.utils.NetWorkHepler1;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.lianhai.zhongchou.utils.NetworkHepler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class FansFollowAdapter extends CommonAdapter<UserInfo> {

    public FansFollowAdapter(Context context, List<UserInfo> list, int resource) {
        super(context, list, resource);
    }

    @Override
    public void setDataToItem(ViewHolder holder, final UserInfo t) {
        CircleImageView touxiang = holder.getView(R.id.touxiang);
        ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu + t.getGravatar(), touxiang, MyApplication.options_image);

        TextView user_name = holder.getView(R.id.user_name);
        if (t.getUsername() != null) {
            user_name.setText(t.getUsername());
        } else {
            user_name.setText("");
        }
        final TextView button = holder.getView(R.id.look);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkHepler hepler = new NetworkHepler();
                hepler.put("sid", t.getId());
                NetWorkUtils.doGet(BaseInfo.Attention, null, hepler, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("response", response.toString());
                        if (response.optString("result") != null)
                            Toast.makeText(context, response.optString("result"), Toast.LENGTH_SHORT).show();
                        if (response.optInt("code") == 1) {
                            button.setText("取消关注");
                            /**
                             * 兼容低版本
                             */
                            button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.corners_boro));
                        } else if(response.optInt("code") == 40){
                            button.setText("添加关注");
                            /**
                             * 兼容低版本
                             */
                            button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.corners_liangyellow));
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("responseString", responseString.toString());
                    }
                });
            }
        });

		/**
		 * 是否被关注
		 */
		NetWorkHepler1 hepler1=new NetWorkHepler1();
		hepler1.put("m","UserLook");
		hepler1.put("a","isLook");
		hepler1.put("sid", t.getId());
		NetWorkUtils.doGet(BaseInfo.BaseUrl_jin, hepler1, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				super.onSuccess(statusCode, headers, response);
                button.setVisibility(View.VISIBLE);
				Log.e("response",response.toString());
				if (response.optInt("code") == 1) {
					button.setText("取消关注");
					/**
					 * 兼容低版本
					 */
					button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.corners_boro));
				}else {
					button.setText("添加关注");
					/**
					 * 兼容低版本
					 */
					button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.corners_liangyellow));
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.e("responseString", responseString.toString());
			}
		});

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, UserZoneActivity.class);
                intent.putExtra("sid", t.getId());
                context.startActivity(intent);
            }
        });

    }

    /**
     * 更新数据
     */
    public void updateData(List<UserInfo> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();

    }

}
