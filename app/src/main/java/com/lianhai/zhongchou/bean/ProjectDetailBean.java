package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaxcler on 15/10/27.
 */
public class ProjectDetailBean implements Parcelable {

    private int id;//项目id
    private String logo;//封面图
    private int favorite;//收藏人数
    private int view;//查看人数
    private String name;//项目名称
    private String introduct;//项目简介
    private int province;//省份id
    private int city;//城市id
    private double pre_value;//预融资金额
    private double min_value;//最小投资额
    private int day;//项目筹资时间
    private double value;//项目固执
    private int create_user;//项目发布人id
    private int leader;//项目领投人id
    private String special_power;//项目股东特权
    private int check_at;//项目审核时间
    private int start_at;//项目开始时间
    private int end_at;//项目结束时间
    private int power;//权限
    private String username;//项目发布人昵称
    private int invests;//项目投资人数
    private int comments;//评论人数
    private double totalMoney;//已认投金额
    private double realMoney;//已交金额

    private String career;//分类

    private CreateUserInfo create_user_info;//发布人信息
    private LeaderInfo leader_info;//领投人信息
    private ArrayList<InvestInfo> invest_info;//投资人信息
    private ArrayList<Repay> repays;//消费公益回报信息


    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public double getPre_value() {
        return pre_value;
    }

    public void setPre_value(double pre_value) {
        this.pre_value = pre_value;
    }

    public double getMin_value() {
        return min_value;
    }

    public void setMin_value(double min_value) {
        this.min_value = min_value;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCreate_user() {
        return create_user;
    }

    public void setCreate_user(int create_user) {
        this.create_user = create_user;
    }

    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }

    public String getSpecial_power() {
        return special_power;
    }

    public void setSpecial_power(String special_power) {
        this.special_power = special_power;
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getInvests() {
        return invests;
    }

    public void setInvests(int invests) {
        this.invests = invests;
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

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public CreateUserInfo getCreate_user_info() {
        return create_user_info;
    }

    public void setCreate_user_info(CreateUserInfo create_user_info) {
        this.create_user_info = create_user_info;
    }

    public LeaderInfo getLeader_info() {
        return leader_info;
    }

    public void setLeader_info(LeaderInfo leader_info) {
        this.leader_info = leader_info;
    }

    public ArrayList<InvestInfo> getInvest_info() {
        return invest_info;
    }

    public void setInvest_info(ArrayList<InvestInfo> invest_info) {
        this.invest_info = invest_info;
    }

    public ArrayList<Repay> getRepays() {
        return repays;
    }

    public void setRepays(ArrayList<Repay> repays) {
        this.repays = repays;
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
        dest.writeString(this.logo);
        dest.writeInt(this.favorite);
        dest.writeInt(this.view);
        dest.writeString(this.name);
        dest.writeString(this.introduct);
        dest.writeInt(this.province);
        dest.writeInt(this.city);
        dest.writeDouble(this.pre_value);
        dest.writeDouble(this.min_value);
        dest.writeInt(this.day);
        dest.writeDouble(this.value);
        dest.writeInt(this.create_user);
        dest.writeInt(this.leader);
        dest.writeString(this.special_power);
        dest.writeInt(this.check_at);
        dest.writeInt(this.start_at);
        dest.writeInt(this.end_at);
        dest.writeInt(this.power);
        dest.writeString(this.username);
        dest.writeInt(this.invests);
        dest.writeInt(this.comments);
        dest.writeDouble(this.totalMoney);
        dest.writeDouble(this.realMoney);
        dest.writeString(this.career);
        dest.writeParcelable(this.create_user_info, 0);
        dest.writeParcelable(this.leader_info, 0);
        dest.writeTypedList(invest_info);
        dest.writeTypedList(repays);
    }

    public ProjectDetailBean() {
    }

    protected ProjectDetailBean(Parcel in) {
        this.id = in.readInt();
        this.logo = in.readString();
        this.favorite = in.readInt();
        this.view = in.readInt();
        this.name = in.readString();
        this.introduct = in.readString();
        this.province = in.readInt();
        this.city = in.readInt();
        this.pre_value = in.readDouble();
        this.min_value = in.readDouble();
        this.day = in.readInt();
        this.value = in.readDouble();
        this.create_user = in.readInt();
        this.leader = in.readInt();
        this.special_power = in.readString();
        this.check_at = in.readInt();
        this.start_at = in.readInt();
        this.end_at = in.readInt();
        this.power = in.readInt();
        this.username = in.readString();
        this.invests = in.readInt();
        this.comments = in.readInt();
        this.totalMoney = in.readDouble();
        this.realMoney = in.readDouble();
        this.career = in.readString();
        this.create_user_info = in.readParcelable(CreateUserInfo.class.getClassLoader());
        this.leader_info = in.readParcelable(LeaderInfo.class.getClassLoader());
        this.invest_info = in.createTypedArrayList(InvestInfo.CREATOR);
        this.repays = in.createTypedArrayList(Repay.CREATOR);
    }

    public static final Creator<ProjectDetailBean> CREATOR = new Creator<ProjectDetailBean>() {
        public ProjectDetailBean createFromParcel(Parcel source) {
            return new ProjectDetailBean(source);
        }

        public ProjectDetailBean[] newArray(int size) {
            return new ProjectDetailBean[size];
        }
    };

    @Override
    public String toString() {
        return "ProjectDetailBean{" +
                "id=" + id +
                ", logo='" + logo + '\'' +
                ", favorite=" + favorite +
                ", view=" + view +
                ", name='" + name + '\'' +
                ", introduct='" + introduct + '\'' +
                ", province=" + province +
                ", city=" + city +
                ", pre_value=" + pre_value +
                ", min_value=" + min_value +
                ", day=" + day +
                ", value=" + value +
                ", create_user=" + create_user +
                ", leader=" + leader +
                ", special_power='" + special_power + '\'' +
                ", check_at=" + check_at +
                ", start_at=" + start_at +
                ", end_at=" + end_at +
                ", power=" + power +
                ", username='" + username + '\'' +
                ", invests=" + invests +
                ", comments=" + comments +
                ", totalMoney=" + totalMoney +
                ", realMoney=" + realMoney +
                ", career='" + career + '\'' +
                ", create_user_info=" + create_user_info +
                ", leader_info=" + leader_info +
                ", invest_info=" + invest_info +
                ", repays=" + repays +
                '}';
    }
}
