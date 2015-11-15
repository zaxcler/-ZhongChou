package com.lianhai.zhongchou.bean;

import org.json.JSONObject;

/**
 * Created by zaxcler on 15/10/23.
 */
public class RootResult {
    private int code; //code=1表示成功 0表示失败
    private String result;//提示信息
    private JSONObject body;//主体内容

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RootResult{" +
                "code=" + code +
                ", result='" + result + '\'' +
                ", body=" + body +
                '}';
    }
}
