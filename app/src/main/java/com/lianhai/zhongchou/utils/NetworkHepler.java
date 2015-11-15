package com.lianhai.zhongchou.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zaxcler on 15/10/26.
 */
public class NetworkHepler {
    public static Map<String,Object> params;
    {
        params=new HashMap<String,Object>();
    }


    public void put(String key,Object object){
        params.put(key,object);
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        Iterator<Map.Entry<String,Object>> it=params.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,Object> entry=it.next();
            builder.append("/"+entry.getKey()+"/"+entry.getValue());
        }
        return builder.toString();
    }
}
