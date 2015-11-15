package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 查询出来的银行卡信息
 * Created by zaxcler on 15/11/7.
 */
public class CardInfo implements Parcelable {
    private String bank_code;

    private String bank_name;

    private String card_key;

    private String card_type;

    private String day_amt;

    private String month_amt;

    private String ret_code;

    private String ret_msg;

    private String sign;

    private String sign_type;

    private String single_amt;

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCard_key() {
        return card_key;
    }

    public void setCard_key(String card_key) {
        this.card_key = card_key;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getDay_amt() {
        return day_amt;
    }

    public void setDay_amt(String day_amt) {
        this.day_amt = day_amt;
    }

    public String getMonth_amt() {
        return month_amt;
    }

    public void setMonth_amt(String month_amt) {
        this.month_amt = month_amt;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSingle_amt() {
        return single_amt;
    }

    public void setSingle_amt(String single_amt) {
        this.single_amt = single_amt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bank_code);
        dest.writeString(this.bank_name);
        dest.writeString(this.card_key);
        dest.writeString(this.card_type);
        dest.writeString(this.day_amt);
        dest.writeString(this.month_amt);
        dest.writeString(this.ret_code);
        dest.writeString(this.ret_msg);
        dest.writeString(this.sign);
        dest.writeString(this.sign_type);
        dest.writeString(this.single_amt);
    }

    public CardInfo() {
    }

    protected CardInfo(Parcel in) {
        this.bank_code = in.readString();
        this.bank_name = in.readString();
        this.card_key = in.readString();
        this.card_type = in.readString();
        this.day_amt = in.readString();
        this.month_amt = in.readString();
        this.ret_code = in.readString();
        this.ret_msg = in.readString();
        this.sign = in.readString();
        this.sign_type = in.readString();
        this.single_amt = in.readString();
    }

    public static final Parcelable.Creator<CardInfo> CREATOR = new Parcelable.Creator<CardInfo>() {
        public CardInfo createFromParcel(Parcel source) {
            return new CardInfo(source);
        }

        public CardInfo[] newArray(int size) {
            return new CardInfo[size];
        }
    };

    @Override
    public String toString() {
        return "CardInfo{" +
                "bank_code='" + bank_code + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", card_key='" + card_key + '\'' +
                ", card_type='" + card_type + '\'' +
                ", day_amt='" + day_amt + '\'' +
                ", month_amt='" + month_amt + '\'' +
                ", ret_code='" + ret_code + '\'' +
                ", ret_msg='" + ret_msg + '\'' +
                ", sign='" + sign + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", single_amt='" + single_amt + '\'' +
                '}';
    }
}
