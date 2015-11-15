package com.lianhai.zhongchou.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.CommentBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.customview.DialogManager;
import com.lianhai.zhongchou.homepage.ProjectDetailActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zaxcler on 15/10/29.
 */
public class CommentAadapter extends CommonAdapter<CommentBean>{

    public CommentAadapter(Context context, List<CommentBean> list, int resource) {
        super(context, list, resource);

    }
    public void add(CommentBean commentBean){
        list.add(commentBean);
    }

    @Override
    public void setDataToItem(ViewHolder holder, final CommentBean commentBean) {

        ImageView user_photo = holder.getView(R.id.user_photo);
        ImageLoader.getInstance().displayImage(BaseInfo.BaseUrl_xu +commentBean.getPath(),user_photo, MyApplication.options_image);

        TextView user_name= holder.getView(R.id.user_name);
        if (commentBean.getUsername()!=null){
            user_name.setText(commentBean.getUsername());
        }else {
            user_name.setText("");
        }
        TextView comment_content= holder.getView(R.id.comment_content);
        if (commentBean.getContent()!=null ){
            if (commentBean.getPname()!=null){
                comment_content.setText("回复"+commentBean.getPname()+":"+commentBean.getContent());
            }else {
                comment_content.setText(commentBean.getContent());
            }

        }else {
            comment_content.setText("");
        }

        Button replay = holder.getView(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectDetailActivity activity= (ProjectDetailActivity) context;
                DialogManager.showCommentReplyDialog(context,commentBean,activity.id);
            }
        });
    }
}
