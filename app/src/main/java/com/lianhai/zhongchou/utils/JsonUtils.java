package com.lianhai.zhongchou.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lianhai.zhongchou.bean.RootResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by zaxcler on 15/10/23.
 * 处理返回json数据
 */

public class JsonUtils {



    /**
     * 根据json数据获取对应的类
     * @param object json数据
     * @return
     */
    public static RootResult getRoot(JSONObject object){
        RootResult result=new RootResult();
        try {
            result.setCode(object.getInt("code"));
            result.setBody(object.getJSONObject("body"));
            result.setResult(object.getString("result"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  result;
    }

    /**
     * 根据json数据获取对应的类
     * @param jsonString 字符串
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T>T  getResult(String jsonString,Class<T> clazz){
        T t=null;
        if(jsonString!=null){
            t= JSON.parseObject(jsonString,clazz);
        }
        return  t;
    }

    /**
     * 根据json对象获取对应的类
     * @param object json对象
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T>T  getResult(JSONObject object,Class<T> clazz){
//        Log.e("json数据",""+object.toString());

        T t=null;
        if(object!=null) {
            t = JSON.parseObject(object.toString(), clazz);
        }
        return  t;
    }


    /**
     * 根据json数据获取对应的集合
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getResultList(String jsonString,Class<T> clazz){
        List<T> arrayList=null;
        if(jsonString!=null){
            arrayList= JSON.parseArray(jsonString, clazz);
        }
        return  arrayList;
    }

    /**
     * 根据json对象数据获取对应的集合
     * @param object  json对象
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> getResultList(JSONArray object,Class<T> clazz){
        List<T> arrayList=null;
        if(object!=null){
            arrayList= JSON.parseArray(object.toString(),clazz);
        }
        return  arrayList;
    }


}
