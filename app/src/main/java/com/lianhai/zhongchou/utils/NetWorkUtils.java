package com.lianhai.zhongchou.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lianhai.zhongchou.config.BaseInfo;
import com.lianhai.zhongchou.config.MyApplication;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaxcler on 15/10/23.
 */
public class NetWorkUtils {

    public static AsyncHttpClient httpClient;//网络请求对象
    private static PersistentCookieStore myCookieStiore;//cookie对象
    private static List<Integer> photoId;//上传后服务器返回的ID


    static {
        httpClient=new AsyncHttpClient();
        myCookieStiore=new PersistentCookieStore(MyApplication.getContext());
        httpClient.setCookieStore(myCookieStiore);
        photoId=new ArrayList<Integer>();
    }



    /**
     * 无参数,无token 的网络请求
     * @param url
     * @param handler
     */
    public static void doGet(String url,JsonHttpResponseHandler handler){

        if (!isNetworkCanUse()){
            Toast.makeText(MyApplication.getContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            return;
        }

            httpClient.get(url,handler);
        Log.e("访问地址", "url"+url);
    }
    /**
     * 有参数,无token 的网络请求
     * @param url
     * @param handler
     */
    public static void doGet(String url,RequestParams params,NetworkHepler hepler,JsonHttpResponseHandler handler){

        if (!isNetworkCanUse()){
            Toast.makeText(MyApplication.getContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            return;
        }
        if (params==null){
            httpClient.get(url + hepler.toString(), handler);
        }else {
            httpClient.get(url, params, handler);
        }
        Log.e("访问地址", "url" + url + hepler.toString());
    }

    /**
     * post请求,金晨程的格式
     * @param url
     * @param params
     * @param handler
     */
    public static void doPost(String url,RequestParams params,NetWorkHepler1 hepler1,AsyncHttpResponseHandler handler){
        if (!isNetworkCanUse()){
            Toast.makeText(MyApplication.getContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            return;
        }

        httpClient.post(url + hepler1.toString(), params, handler);
        Log.e("post", url + hepler1.toString());
        if (params!=null){
            Log.e("post", url + hepler1.toString()+"   后面是post参数"+params.toString());
        }
    }


    /**
     * get请求，金晨程的格式
     * @param url
     * @param hepler1 参数
     * @param handler
     */
    public static void doGet(String url,NetWorkHepler1 hepler1,AsyncHttpResponseHandler handler){
        if (!isNetworkCanUse()){
            Toast.makeText(MyApplication.getContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            return;
        }
        httpClient.post(url + hepler1.toString(), handler);
        Log.e("doGet——jin", url + hepler1.toString());

    }

    /**
     * 正常post请求
     * @param url
     * @param params
     * @param handler
     */
    public static void doPost(String url,RequestParams params,AsyncHttpResponseHandler handler){
        if (!isNetworkCanUse()){
            Toast.makeText(MyApplication.getContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            return;
        }
        httpClient.post(url, params, handler);
        Log.e("url",url+"/"+params.toString());
    }



    /**
     * 检查网络是否可用
     * @return
     */
    public static boolean isNetworkCanUse(){
        Context context=MyApplication.getContext();
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager==null){
            return  false;
        }else {
            if (manager.getActiveNetworkInfo() == null || !manager.getActiveNetworkInfo().isAvailable()) {
                return false;
            }
        }
        return true;
    }






}
