package com.lianhai.zhongchou.bean;

/**
 * Created by zaxcler on 15/10/23.
 */
public class FocusBean {
    private String url;
    private String title;
    private String path;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "FocusBean{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
