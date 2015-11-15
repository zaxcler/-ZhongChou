package com.lianhai.zhongchou.bean;

/**
 * Created by zaxcler on 15/11/2.
 */
public class IntroduceBean {
    private int id;
    private String description;
    private String future;
    private String customer;
    private String compete;
    private String goodness;
    private String profit;
    private String teams;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFuture() {
        return future;
    }

    public void setFuture(String future) {
        this.future = future;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCompete() {
        return compete;
    }

    public void setCompete(String compete) {
        this.compete = compete;
    }

    public String getGoodness() {
        return goodness;
    }

    public void setGoodness(String goodness) {
        this.goodness = goodness;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "IntroduceBean{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", future='" + future + '\'' +
                ", customer='" + customer + '\'' +
                ", compete='" + compete + '\'' +
                ", goodness='" + goodness + '\'' +
                ", profit='" + profit + '\'' +
                ", teams='" + teams + '\'' +
                '}';
    }
}
