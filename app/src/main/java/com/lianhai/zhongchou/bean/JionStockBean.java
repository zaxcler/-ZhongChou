package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxcler on 15/11/6.
 * 认投类封装
 */
public class JionStockBean implements Parcelable {

    private int id;//项目id
    private double money;//认投金额
    private int leader_type;//是否领投 0：不领投  1：领投
    private String reason;//领投理由
    private String remark;//领投人寄语

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getLeader_type() {
        return leader_type;
    }

    public void setLeader_type(int leader_type) {
        this.leader_type = leader_type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.money);
        dest.writeInt(this.leader_type);
        dest.writeString(this.reason);
        dest.writeString(this.remark);
    }

    public JionStockBean() {
    }

    protected JionStockBean(Parcel in) {
        this.id = in.readInt();
        this.money = in.readDouble();
        this.leader_type = in.readInt();
        this.reason = in.readString();
        this.remark = in.readString();
    }

    public static final Parcelable.Creator<JionStockBean> CREATOR = new Parcelable.Creator<JionStockBean>() {
        public JionStockBean createFromParcel(Parcel source) {
            return new JionStockBean(source);
        }

        public JionStockBean[] newArray(int size) {
            return new JionStockBean[size];
        }
    };

    @Override
    public String toString() {
        return "JionStockBean{" +
                "id=" + id +
                ", money=" + money +
                ", leader_type=" + leader_type +
                ", reason='" + reason + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
