package com.lianhai.zhongchou.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TestBean implements Parcelable {
    private int id;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
    }

    public TestBean() {
    }

    protected TestBean(Parcel in) {
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<TestBean> CREATOR = new Parcelable.Creator<TestBean>() {
        public TestBean createFromParcel(Parcel source) {
            return new TestBean(source);
        }

        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };
}
