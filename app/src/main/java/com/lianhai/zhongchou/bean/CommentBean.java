package com.lianhai.zhongchou.bean;

/**
 * Created by zaxcler on 15/10/28.
 */
public class CommentBean {
    private int id;
    private String content;//评论内容
    private int user_id;//用户id
    private String username;//用户昵称
    private String path;//用户头像
    private String pname;//被回复人昵称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    
    @Override
    public String toString() {
        return "CommentBean{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user_id=" + user_id +
                ", username='" + username + '\'' +
                ", path='" + path + '\'' +
                ", pname='" + pname + '\'' +
                '}';
    }
}
