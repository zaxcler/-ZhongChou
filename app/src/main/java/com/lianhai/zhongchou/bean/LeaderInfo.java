package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxlcer on 15/10/27.
 */
public class LeaderInfo implements Parcelable {
    private int id;
    private String username;
    private String user_img;
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "LeaderInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", user_img='" + user_img + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.user_img);
        dest.writeString(this.remark);
    }

    public LeaderInfo() {
    }

    protected LeaderInfo(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.user_img = in.readString();
        this.remark = in.readString();
    }

    public static final Parcelable.Creator<LeaderInfo> CREATOR = new Parcelable.Creator<LeaderInfo>() {
        public LeaderInfo createFromParcel(Parcel source) {
            return new LeaderInfo(source);
        }

        public LeaderInfo[] newArray(int size) {
            return new LeaderInfo[size];
        }
    };
}
