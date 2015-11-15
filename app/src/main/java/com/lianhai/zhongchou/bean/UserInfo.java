package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxcler on 15/10/30.
 */
public class UserInfo implements Parcelable {

    private String role;//类型

    private String telephone_auth;

    private int auth_status;//实名认证 0：未申请 1：通过 2：正在审核

    private String logtime;

    private String status;

    private String auth_type;

    private String identify;

    private String email_auth;

    private String password;

    private String identify_zheng;

    private String identify_fan;

    private String ip;

    private int id;

    private String regtime;

    private String username;

    private String email;

    private String gravatar;

    private String money;

    private String realname;

    private String telephone;

    private String salt;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTelephone_auth() {
        return telephone_auth;
    }

    public void setTelephone_auth(String telephone_auth) {
        this.telephone_auth = telephone_auth;
    }

    public int getAuth_status() {
        return auth_status;
    }

    public void setAuth_status(int auth_status) {
        this.auth_status = auth_status;
    }

    public String getLogtime() {
        return logtime;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuth_type() {
        return auth_type;
    }

    public void setAuth_type(String auth_type) {
        this.auth_type = auth_type;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getEmail_auth() {
        return email_auth;
    }

    public void setEmail_auth(String email_auth) {
        this.email_auth = email_auth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentify_zheng() {
        return identify_zheng;
    }

    public void setIdentify_zheng(String identify_zheng) {
        this.identify_zheng = identify_zheng;
    }

    public String getIdentify_fan() {
        return identify_fan;
    }

    public void setIdentify_fan(String identify_fan) {
        this.identify_fan = identify_fan;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegtime() {
        return regtime;
    }

    public void setRegtime(String regtime) {
        this.regtime = regtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGravatar() {
        return gravatar;
    }

    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "role='" + role + '\'' +
                ", telephone_auth='" + telephone_auth + '\'' +
                ", auth_status='" + auth_status + '\'' +
                ", logtime='" + logtime + '\'' +
                ", status='" + status + '\'' +
                ", auth_type='" + auth_type + '\'' +
                ", identify='" + identify + '\'' +
                ", email_auth='" + email_auth + '\'' +
                ", password='" + password + '\'' +
                ", identify_zheng='" + identify_zheng + '\'' +
                ", identify_fan='" + identify_fan + '\'' +
                ", ip='" + ip + '\'' +
                ", id=" + id +
                ", regtime='" + regtime + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", gravatar='" + gravatar + '\'' +
                ", money='" + money + '\'' +
                ", realname='" + realname + '\'' +
                ", telephone='" + telephone + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.role);
        dest.writeString(this.telephone_auth);
        dest.writeInt(this.auth_status);
        dest.writeString(this.logtime);
        dest.writeString(this.status);
        dest.writeString(this.auth_type);
        dest.writeString(this.identify);
        dest.writeString(this.email_auth);
        dest.writeString(this.password);
        dest.writeString(this.identify_zheng);
        dest.writeString(this.identify_fan);
        dest.writeString(this.ip);
        dest.writeInt(this.id);
        dest.writeString(this.regtime);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.gravatar);
        dest.writeString(this.money);
        dest.writeString(this.realname);
        dest.writeString(this.telephone);
        dest.writeString(this.salt);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.role = in.readString();
        this.telephone_auth = in.readString();
        this.auth_status = in.readInt();
        this.logtime = in.readString();
        this.status = in.readString();
        this.auth_type = in.readString();
        this.identify = in.readString();
        this.email_auth = in.readString();
        this.password = in.readString();
        this.identify_zheng = in.readString();
        this.identify_fan = in.readString();
        this.ip = in.readString();
        this.id = in.readInt();
        this.regtime = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.gravatar = in.readString();
        this.money = in.readString();
        this.realname = in.readString();
        this.telephone = in.readString();
        this.salt = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
