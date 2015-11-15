package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zaxcler on 15/11/6.
 */
public class RepayDetailBean implements Parcelable {

    private int applyNum;//已支持人数
    private ProjectBean project;
    private Repay repay;//回报内容
    private AddressBean addr;

    public int getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(int applyNum) {
        this.applyNum = applyNum;
    }

    public ProjectBean getProject() {
        return project;
    }

    public void setProject(ProjectBean project) {
        this.project = project;
    }

    public Repay getRepay() {
        return repay;
    }

    public void setRepay(Repay repay) {
        this.repay = repay;
    }

    public AddressBean getAddr() {
        return addr;
    }

    public void setAddr(AddressBean addr) {
        this.addr = addr;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.applyNum);
        dest.writeParcelable(this.project, 0);
        dest.writeParcelable(this.repay, 0);
        dest.writeParcelable(this.addr, 0);
    }

    public RepayDetailBean() {
    }

    protected RepayDetailBean(Parcel in) {
        this.applyNum = in.readInt();
        this.project = in.readParcelable(ProjectBean.class.getClassLoader());
        this.repay = in.readParcelable(Repay.class.getClassLoader());
        this.addr = in.readParcelable(AddressBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<RepayDetailBean> CREATOR = new Parcelable.Creator<RepayDetailBean>() {
        public RepayDetailBean createFromParcel(Parcel source) {
            return new RepayDetailBean(source);
        }

        public RepayDetailBean[] newArray(int size) {
            return new RepayDetailBean[size];
        }
    };

    @Override
    public String
    toString() {
        return "RepayDetailBean{" +
                "applyNum=" + applyNum +
                ", project=" + project +
                ", repay=" + repay +
                ", addr=" + addr +
                '}';
    }
}
