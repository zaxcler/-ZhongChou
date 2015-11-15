package com.lianhai.zhongchou.bean;

import java.util.List;

/**
 * Created by zaxcler on 15/10/23.
 */
public class HomePageBean {

    private List<FocusBean> focus;
    private List<ProjectBean> project;


    public List<FocusBean> getFocus() {
        return focus;
    }

    public void setFocus(List<FocusBean> focus) {
        this.focus = focus;
    }

    public List<ProjectBean> getProject() {
        return project;
    }

    public void setProject(List<ProjectBean> project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "HomePageBean{" +
                "focus=" + focus +
                ", project=" + project +
                '}';
    }
}
