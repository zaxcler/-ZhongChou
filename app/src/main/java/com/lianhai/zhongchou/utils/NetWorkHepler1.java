package com.lianhai.zhongchou.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zaxcler on 15/10/29.
 */
public class NetWorkHepler1 {
    public static Map<String,Object> params;


    public NetWorkHepler1(){
        params=new HashMap<String,Object>();
    }

    public void put(String mode,Object method){
        params.put(mode,method);
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("?");
        Iterator<Map.Entry<String,Object>> it=params.entrySet().iterator();
        do{
            Map.Entry<String,Object> entry=it.next();
            builder.append( entry.getKey() + "=" + entry.getValue());
            if (it.hasNext()){
                builder.append("&");
            }
        }while (it.hasNext());
        return builder.toString();
    }
}
