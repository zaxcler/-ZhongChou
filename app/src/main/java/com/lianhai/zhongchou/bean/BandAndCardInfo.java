package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 获取的绑定的银行卡的信息
 * Created by asdfgh on 15/11/7.
 */
public class BandAndCardInfo implements Parcelable {


    private String dibs;

    private String prepaid;

    private int uid;

    private int id;

    private String brabank_name;

    private String province_code;

    private String acct_name;

    private String city_code;

    private String flag_card;

    private String card_no;

    private String bank_code;

    private String extract;

    private String id_no;


    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getDibs() {
        return dibs;
    }

    public void setDibs(String dibs) {
        this.dibs = dibs;
    }

    public String getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(String prepaid) {
        this.prepaid = prepaid;
    }

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

    public String getBrabank_name() {
        return brabank_name;
    }

    public void setBrabank_name(String brabank_name) {
        this.brabank_name = brabank_name;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getAcct_name() {
        return acct_name;
    }

    public void setAcct_name(String acct_name) {
        this.acct_name = acct_name;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getFlag_card() {
        return flag_card;
    }

    public void setFlag_card(String flag_card) {
        this.flag_card = flag_card;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }


    @Override
    public String toString() {
        return "BandAndCardInfo{" +
                "dibs='" + dibs + '\'' +
                ", prepaid='" + prepaid + '\'' +
                ", uid=" + uid +
                ", id=" + id +
                ", brabank_name='" + brabank_name + '\'' +
                ", province_code='" + province_code + '\'' +
                ", acct_name='" + acct_name + '\'' +
                ", city_code='" + city_code + '\'' +
                ", flag_card='" + flag_card + '\'' +
                ", card_no='" + card_no + '\'' +
                ", bank_code='" + bank_code + '\'' +
                ", extract='" + extract + '\'' +
                ", id_no='" + id_no + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dibs);
        dest.writeString(this.prepaid);
        dest.writeInt(this.uid);
        dest.writeInt(this.id);
        dest.writeString(this.brabank_name);
        dest.writeString(this.province_code);
        dest.writeString(this.acct_name);
        dest.writeString(this.city_code);
        dest.writeString(this.flag_card);
        dest.writeString(this.card_no);
        dest.writeString(this.bank_code);
        dest.writeString(this.extract);
        dest.writeString(this.id_no);
    }

    public BandAndCardInfo() {
    }

    protected BandAndCardInfo(Parcel in) {
        this.dibs = in.readString();
        this.prepaid = in.readString();
        this.uid = in.readInt();
        this.id = in.readInt();
        this.brabank_name = in.readString();
        this.province_code = in.readString();
        this.acct_name = in.readString();
        this.city_code = in.readString();
        this.flag_card = in.readString();
        this.card_no = in.readString();
        this.bank_code = in.readString();
        this.extract = in.readString();
        this.id_no = in.readString();
    }

    public static final Parcelable.Creator<BandAndCardInfo> CREATOR = new Parcelable.Creator<BandAndCardInfo>() {
        public BandAndCardInfo createFromParcel(Parcel source) {
            return new BandAndCardInfo(source);
        }

        public BandAndCardInfo[] newArray(int size) {
            return new BandAndCardInfo[size];
        }
    };
}
