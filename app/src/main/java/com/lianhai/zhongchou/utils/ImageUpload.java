package com.lianhai.zhongchou.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传图片
 * Created by zaxcler on 15/11/2.
 */
public class ImageUpload {


    public static AlertDialog dialog;
    public static List<Uri> uriList;
    public static List<Integer> photoId;

    public static UploadListener listener;
    static  {
        uriList =new ArrayList<Uri>();
        photoId=new ArrayList<Integer>();
    }

    /**
     * 多图上传
     * @param list
     */
    public static void upLoadImage(List<Uri> list){
        uriList =list;
        NetWorkHepler1 hepler1=new NetWorkHepler1();
        hepler1.put("m", "index");
        hepler1.put("a", "img");
        for (Uri uri:list) {
            RequestParams parms=new RequestParams();
            File file=new File(ImageUriUtils.getPath(MyApplication.getContext(),uri));
            if (!file.exists()){
                Log.e("图片路径有误",uri.getPath());
            }else{
                try {
                    parms.put("image",file);
                    Bitmap bitmap= BitmapFactory.decodeFile(ImageUriUtils.getPath(MyApplication.getContext(),uri));
                    hepler1.put("width",bitmap.getWidth());

                    NetWorkUtils.doPost(BaseInfo.BaseUrl_jin, parms, hepler1, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.e("response", response.toString());
                            Message msg = Message.obtain();
                            msg.obj=response;
                            if (response.optInt("code") == 1) {
                                msg.what = 1;
                                if (response.optJSONObject("body") != null) {

                                    msg.arg1 = response.optJSONObject("body").optInt("id");
                                    photoId.add(msg.arg1);
                                }
                            } else {
                                msg.what = 0;
                            }
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            handler.sendEmptyMessage(0);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.e("e", uri.getPath());
                }
            }


        }



    }

    public static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    dialog.setMessage("图片上传失败");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    }, 2000);
                    photoId.clear();
                    if (listener!=null){
                    listener.onFailed(msg.obj);
                    }
                    NetWorkUtils.httpClient.cancelAllRequests(true);
                    break;
                case 1:
                    dialog.setMessage("上传图片...  " + photoId.size() + "/" + uriList.size());
                    if (photoId.size()>=uriList.size()){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        }, 2000);
                        if (listener!=null) {
                            listener.onSuccess(msg.obj);
                        }
                    }
                    break;
            }
        }
    };

    public static void showUploadDialog(Activity activity){
        dialog=new AlertDialog.Builder(activity,AlertDialog.THEME_HOLO_LIGHT).create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setMessage("上传图片...  " + photoId.size() + "/" + uriList.size());
        dialog.show();
    }

    public interface UploadListener {
        public void onFailed(Object error);
        public void onSuccess(Object result);
    }

    /**
     * 设置监听
     * @param uploadListener
     */
    public static void setUploadListener(UploadListener uploadListener){

        listener=uploadListener;
    }


}
