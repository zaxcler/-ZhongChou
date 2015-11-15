package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxcler on 15/11/7.
 */
public class OrderInfo implements Parcelable {
    private String ordersn;//订单号
    private String money;
    private String time;
    private String create_order_time;
    private String name;
    private String realname;
    private String telephone;
    private String identify;
    private String id;
    private String cardId;
    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreate_order_time() {
        return create_order_time;
    }

    public void setCreate_order_time(String create_order_time) {
        this.create_order_time = create_order_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "ordersn='" + ordersn + '\'' +
                ", money='" + money + '\'' +
                ", time='" + time + '\'' +
                ", create_order_time='" + create_order_time + '\'' +
                ", name='" + name + '\'' +
                ", realname='" + realname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", identify='" + identify + '\'' +
                ", id='" + id + '\'' +
                ", cardId='" + cardId + '\'' +
                ", type=" + type +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ordersn);
        dest.writeString(this.money);
        dest.writeString(this.time);
        dest.writeString(this.create_order_time);
        dest.writeString(this.name);
        dest.writeString(this.realname);
        dest.writeString(this.telephone);
        dest.writeString(this.identify);
        dest.writeString(this.id);
        dest.writeString(this.cardId);
        dest.writeInt(this.type);
    }

    public OrderInfo() {
    }

    protected OrderInfo(Parcel in) {
        this.ordersn = in.readString();
        this.money = in.readString();
        this.time = in.readString();
        this.create_order_time = in.readString();
        this.name = in.readString();
        this.realname = in.readString();
        this.telephone = in.readString();
        this.identify = in.readString();
        this.id = in.readString();
        this.cardId = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        public OrderInfo createFromParcel(Parcel source) {
            return new OrderInfo(source);
        }

        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
}
