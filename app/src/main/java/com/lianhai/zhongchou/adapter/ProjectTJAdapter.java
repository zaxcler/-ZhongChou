package com.lianhai.zhongchou.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.ProjectBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zaxcler on 15/10/30.
 */
public class ProjectTJAdapter extends CommonAdapter<ProjectBean>{

    public ProjectTJAdapter(Context context, List<ProjectBean> list, int resource) {
        super(context, list, resource);
    }

    @Override
    public void setDataToItem(ViewHolder holder, ProjectBean projectBean) {
        ImageView project_img=holder.getView(R.id.project_img);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,MyApplication.getScreen_width()/3);
        project_img.setLayoutParams(params);
        ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu+projectBean.getLogo(),project_img, MyApplication.options_image);

        TextView project_name=holder.getView(R.id.project_name);
        if (projectBean.getName()!=null){
            project_name.setText(projectBean.getName());
        }else {
            project_name.setText("");
        }


    }
}
