package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxcler on 15/11/3.
 */
public class AddressBean implements Parcelable {
    private int uid;

    private int id;

    private int province;


    private String time;

    private String address;

    private String name;

    private String zipcode;

    private String province_name;

    private String telephone;

    private int city;

    private String city_name;

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

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "uid=" + uid +
                ", id=" + id +
                ", province=" + province +
                ", time='" + time + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", province_name='" + province_name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", city=" + city +
                ", city_name='" + city_name + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeInt(this.id);
        dest.writeInt(this.province);
        dest.writeString(this.time);
        dest.writeString(this.address);
        dest.writeString(this.name);
        dest.writeString(this.zipcode);
        dest.writeString(this.province_name);
        dest.writeString(this.telephone);
        dest.writeInt(this.city);
        dest.writeString(this.city_name);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.uid = in.readInt();
        this.id = in.readInt();
        this.province = in.readInt();
        this.time = in.readString();
        this.address = in.readString();
        this.name = in.readString();
        this.zipcode = in.readString();
        this.province_name = in.readString();
        this.telephone = in.readString();
        this.city = in.readInt();
        this.city_name = in.readString();
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}

