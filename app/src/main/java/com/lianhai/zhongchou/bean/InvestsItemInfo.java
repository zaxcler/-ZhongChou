package com.lianhai.zhongchou.bean;

/**
 * Created by zaxcler on 15/11/5.
 */
public class InvestsItemInfo {

    private int uid;//用户id
    private int id;//对应数据库表中的id
    private String time;//时间
    private String reason;//原因
    private String premoney;//预融资金额
    private String name;//名字
    private String gravatar;//图片
    private int status;//状态
    private String paymoney;//投资的钱


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPremoney() {
        return premoney;
    }

    public void setPremoney(String premoney) {
        this.premoney = premoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGravatar() {
        return gravatar;
    }

    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPaymoney() {
        return paymoney;
    }

    public void setPaymoney(String paymoney) {
        this.paymoney = paymoney;
    }


    @Override
    public String toString() {
        return "InvestsItemInfo{" +
                "uid=" + uid +
                ", id=" + id +
                ", time='" + time + '\'' +
                ", reason='" + reason + '\'' +
                ", premoney='" + premoney + '\'' +
                ", name='" + name + '\'' +
                ", gravatar='" + gravatar + '\'' +
                ", status=" + status +
                ", paymoney='" + paymoney + '\'' +
                '}';
    }
}
