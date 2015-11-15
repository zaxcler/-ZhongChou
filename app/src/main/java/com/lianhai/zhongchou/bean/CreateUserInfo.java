package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxcler on 15/10/27.
 */
public class CreateUserInfo implements Parcelable {

    private int id;
    private String username;
    private String user_img;

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

    @Override
    public String toString() {
        return "CreateUserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", user_img='" + user_img + '\'' +
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
    }

    public CreateUserInfo() {
    }

    protected CreateUserInfo(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.user_img = in.readString();
    }

    public static final Parcelable.Creator<CreateUserInfo> CREATOR = new Parcelable.Creator<CreateUserInfo>() {
        public CreateUserInfo createFromParcel(Parcel source) {
            return new CreateUserInfo(source);
        }

        public CreateUserInfo[] newArray(int size) {
            return new CreateUserInfo[size];
        }
    };
}
