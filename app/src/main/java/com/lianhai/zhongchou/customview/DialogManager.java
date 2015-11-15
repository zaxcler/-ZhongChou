package com.lianhai.zhongchou.customview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.CommentBean;
import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.lianhai.zhongchou.homepage.ProjectDetailActivity;
import com.lianhai.zhongchou.utils.NetWorkUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by zaxcler on 15/10/29.
 */
public class DialogManager {


    /**
     * 弹出评论窗口
     * @param context
     * @param comment
     * @param projectId
     */
    public static void showCommentReplyDialog(final Context context, final CommentBean comment, final int projectId) {


        final ProjectDetailActivity activity= (ProjectDetailActivity) context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.comment_reply_dialog, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        if (comment != null) {
            if (comment.getUsername() != null) {
                title.setText("回复" + comment.getUsername());
            }
        }else {
            title.setText("评论");
        }

        final EditText editText = (EditText) view.findViewById(R.id.editText);

        final TextView cancle = (TextView) view.findViewById(R.id.cancle);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);

        final AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
        params.width= MyApplication.getScreen_width()*2/3;
        params.height= WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.setCancelable(false);
        dialog.setContentView(view);


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = editText.getText().toString().trim();
                RequestParams params = new RequestParams();
                params.put("content", content);
                params.put("id", projectId);
                if (comment != null) {
                    params.put("parent_id", comment.getUser_id());
                }

                NetWorkUtils.doPost(BaseInfo.Comment, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        if (response.optInt("code") == 1) {
                            CommentBean commentBean=new CommentBean();
                            commentBean.setContent(content);
                            commentBean.setId(MyApplication.preferences.getInt("UserId", 0));
                            commentBean.setPath(MyApplication.preferences.getString("Gravatar", ""));
                            if (comment!=null){
                                commentBean.setPname(comment.getUsername());
                            }
                            commentBean.setUsername(MyApplication.preferences.getString("UserName",""));
                            activity.adapter.add(commentBean);
                            dialog.dismiss();
                        }
                        if (response.optString("result") != null) {
                            Toast.makeText(context, response.optString("result"), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(context, responseString, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

    /**
     * 显示webview加载信息
     */
    public static void showWebViewDialog(Context context,String url){
        AlertDialog.Builder builder=new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        WebView webView=new WebView(context);
//        webView.getSettings().setJavaScriptEnabled(true);
        builder.setView(webView);
        webView.loadUrl(url);
        AlertDialog dialog=builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    /**
     * 显示富文本信息
     */
    public static void showFWBDialog(Context context,String html){
        AlertDialog.Builder builder=new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        WebView webView=new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
        ViewGroup.LayoutParams wlp=new ViewGroup.LayoutParams(MyApplication.getScreen_width()-30,MyApplication.getScreen_height()*2/3);
        webView.setLayoutParams(wlp);
        webView.loadDataWithBaseURL(null,html,"text/html", "utf-8", null);
//        ZXUtils.showFWB(textView,html);
        builder.setView(webView);
        AlertDialog dialog=builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }



    /**
     * 显示提示信息
     */
    public static void showNotice(Context context,String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_DARK);
        final AlertDialog dialog=builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wl=dialog.getWindow().getAttributes();
        wl.alpha=0.7f;
        dialog.getWindow().setAttributes(wl);
        dialog.setMessage(msg);
        dialog.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },2000);

    }


}
