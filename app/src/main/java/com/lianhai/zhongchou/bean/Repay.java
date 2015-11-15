package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxcler on 15/10/28.
 */
public class Repay implements Parcelable {
    private int id;
    private int type;//回报类型： 0：虚拟物品 1：实物回报
    private double money;//支持资金
    private String content;//回报内容
    private int num;//限定名额
    private double ship_money;//运费
    private int day;//商品发放时间
    private int entail;//单笔限购
    private String img;
    private String award;
    private String oid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getShip_money() {
        return ship_money;
    }

    public void setShip_money(double ship_money) {
        this.ship_money = ship_money;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getEntail() {
        return entail;
    }

    public void setEntail(int entail) {
        this.entail = entail;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }


    @Override
    public String toString() {
        return "Repay{" +
                "id=" + id +
                ", type=" + type +
                ", money=" + money +
                ", content='" + content + '\'' +
                ", num=" + num +
                ", ship_money=" + ship_money +
                ", day=" + day +
                ", entail=" + entail +
                ", img='" + img + '\'' +
                ", award='" + award + '\'' +
                ", oid='" + oid + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.type);
        dest.writeDouble(this.money);
        dest.writeString(this.content);
        dest.writeInt(this.num);
        dest.writeDouble(this.ship_money);
        dest.writeInt(this.day);
        dest.writeInt(this.entail);
        dest.writeString(this.img);
        dest.writeString(this.award);
        dest.writeString(this.oid);
    }

    public Repay() {
    }

    protected Repay(Parcel in) {
        this.id = in.readInt();
        this.type = in.readInt();
        this.money = in.readDouble();
        this.content = in.readString();
        this.num = in.readInt();
        this.ship_money = in.readDouble();
        this.day = in.readInt();
        this.entail = in.readInt();
        this.img = in.readString();
        this.award = in.readString();
        this.oid = in.readString();
    }

    public static final Creator<Repay> CREATOR = new Creator<Repay>() {
        public Repay createFromParcel(Parcel source) {
            return new Repay(source);
        }

        public Repay[] newArray(int size) {
            return new Repay[size];
        }
    };
}
