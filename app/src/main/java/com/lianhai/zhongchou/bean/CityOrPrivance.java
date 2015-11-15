package com.lianhai.zhongchou.bean;

/**
 * Created by zaxcler on 15/11/3.
 */
public class CityOrPrivance {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityOrPrivance{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
