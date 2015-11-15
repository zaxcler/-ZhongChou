package com.lianhai.zhongchou.bean;

/**
 * Created by zaxcler on 15/11/4.
 */
public class CashFollowBean {
    private String time;
    private String money;//交易金额
    private int type; //0 收入  1支出
    private int paytype;//支付类型  0：投资 1：线下充值 2：线上充值 3：提现 4：其他
    private String note;//交易备注
    private double current;//剩余金额

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPaytype() {
        return paytype;
    }

    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "CashFollowBean{" +
                "time='" + time + '\'' +
                ", money='" + money + '\'' +
                ", type=" + type +
                ", paytype=" + paytype +
                ", note='" + note + '\'' +
                ", current=" + current +
                '}';
    }
}
