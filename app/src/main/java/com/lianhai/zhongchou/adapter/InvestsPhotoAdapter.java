package com.lianhai.zhongchou.adapter;

import android.content.Context;
import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.InvestInfo;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zaxcler on 15/10/28.
 */
public class InvestsPhotoAdapter extends CommonAdapter<InvestInfo> {
    public InvestsPhotoAdapter(Context context, List<InvestInfo> list, int resource) {
        super(context, list, resource);
    }

    @Override
    public void setDataToItem(ViewHolder holder, InvestInfo investInfo) {
       CircleImageView image= holder.getView(R.id.image);
        ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu +investInfo.getUser_img(),image, MyApplication.options_image);

    }
}
