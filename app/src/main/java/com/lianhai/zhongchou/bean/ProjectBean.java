package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxcler on 15/10/23.
 *
 */
public class ProjectBean implements Parcelable {
    private int id;//项目id
    private int type;//项目类型 0：股权 1：消费项目 2：公益项目
    private String name;//项目名称
    private String username;//发布人用户名
    private String realname;//发布人真名
    private String logo;//项目封面图
    private int favorite;//项目收藏人数
    private int check_at;//项目审核时间
    private int start_at;//项目开始时间
    private int end_at;//项目结束时间
    private int comments;//项目评论人数
    private double totalMoney;//项目认投额
    private double realMoney;//已投资的钱
    private int view;//项目浏览人数
    private int city;//项目所处城市
    private int province;//项目所处省份
    private double pre_value;//项目预融资金额
    private int status;//状态 0:认头 1：已签协议 2：已经支付
    private int where;//从哪儿进，标志是哪个  0：我的投资  1：我的收藏 2：我的发起 （自己定义的为了区别）

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getCheck_at() {
        return check_at;
    }

    public void setCheck_at(int check_at) {
        this.check_at = check_at;
    }

    public int getStart_at() {
        return start_at;
    }

    public void setStart_at(int start_at) {
        this.start_at = start_at;
    }

    public int getEnd_at() {
        return end_at;
    }

    public void setEnd_at(int end_at) {
        this.end_at = end_at;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public double getPre_value() {
        return pre_value;
    }

    public void setPre_value(double pre_value) {
        this.pre_value = pre_value;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWhere() {
        return where;
    }

    public void setWhere(int where) {
        this.where = where;
    }

    public double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(double realMoney) {
        this.realMoney = realMoney;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeString(this.username);
        dest.writeString(this.realname);
        dest.writeString(this.logo);
        dest.writeInt(this.favorite);
        dest.writeInt(this.check_at);
        dest.writeInt(this.start_at);
        dest.writeInt(this.end_at);
        dest.writeInt(this.comments);
        dest.writeDouble(this.totalMoney);
        dest.writeDouble(this.realMoney);
        dest.writeInt(this.view);
        dest.writeInt(this.city);
        dest.writeInt(this.province);
        dest.writeDouble(this.pre_value);
        dest.writeInt(this.status);
        dest.writeInt(this.where);
    }

    public ProjectBean() {
    }

    protected ProjectBean(Parcel in) {
        this.id = in.readInt();
        this.type = in.readInt();
        this.name = in.readString();
        this.username = in.readString();
        this.realname = in.readString();
        this.logo = in.readString();
        this.favorite = in.readInt();
        this.check_at = in.readInt();
        this.start_at = in.readInt();
        this.end_at = in.readInt();
        this.comments = in.readInt();
        this.totalMoney = in.readDouble();
        this.realMoney = in.readDouble();
        this.view = in.readInt();
        this.city = in.readInt();
        this.province = in.readInt();
        this.pre_value = in.readDouble();
        this.status = in.readInt();
        this.where = in.readInt();
    }

    public static final Creator<ProjectBean> CREATOR = new Creator<ProjectBean>() {
        public ProjectBean createFromParcel(Parcel source) {
            return new ProjectBean(source);
        }

        public ProjectBean[] newArray(int size) {
            return new ProjectBean[size];
        }
    };


    @Override
    public String toString() {
        return "ProjectBean{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", logo='" + logo + '\'' +
                ", favorite=" + favorite +
                ", check_at=" + check_at +
                ", start_at=" + start_at +
                ", end_at=" + end_at +
                ", comments=" + comments +
                ", totalMoney=" + totalMoney +
                ", realMoney=" + realMoney +
                ", view=" + view +
                ", city=" + city +
                ", province=" + province +
                ", pre_value=" + pre_value +
                ", status=" + status +
                ", where=" + where +
                '}';
    }
}
