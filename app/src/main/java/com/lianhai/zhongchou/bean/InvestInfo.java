package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxcler on 15/10/27.
 */
public class InvestInfo implements Parcelable {
    private int uid;
    private String username;
    private String user_img;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    @Override
    public String toString() {
        return "InvestInfo{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", user_img=" + user_img +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.username);
        dest.writeString(this.user_img);
    }

    public InvestInfo() {
    }

    protected InvestInfo(Parcel in) {
        this.uid = in.readInt();
        this.username = in.readString();
        this.user_img = in.readString();
    }

    public static final Parcelable.Creator<InvestInfo> CREATOR = new Parcelable.Creator<InvestInfo>() {
        public InvestInfo createFromParcel(Parcel source) {
            return new InvestInfo(source);
        }

        public InvestInfo[] newArray(int size) {
            return new InvestInfo[size];
        }
    };
}
