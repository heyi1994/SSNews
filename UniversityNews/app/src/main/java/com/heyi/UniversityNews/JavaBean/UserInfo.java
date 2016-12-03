package com.heyi.UniversityNews.JavaBean;

/**
 * Created by heyi on 2016/12/2.
 */

public class UserInfo {
    private int user_id;
    private String user_nick_name;
    private String user_password;
    private String user_phone;
    private String user_icon_url;
    private String user_signature;
    private int user_like_num;
    private int user_dislike_num;
    private int user_comment_num;
    private int user_collect_num;
    private String user_register_date;
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getUser_nick_name() {
        return user_nick_name;
    }
    public void setUser_nick_name(String user_nick_name) {
        this.user_nick_name = user_nick_name;
    }
    public String getUser_password() {
        return user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public String getUser_phone() {
        return user_phone;
    }
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
    public String getUser_icon_url() {
        return user_icon_url;
    }
    public void setUser_icon_url(String user_icon_url) {
        this.user_icon_url = user_icon_url;
    }
    public String getUser_signature() {
        return user_signature;
    }
    public void setUser_signature(String user_signature) {
        this.user_signature = user_signature;
    }
    public int getUser_like_num() {
        return user_like_num;
    }
    public void setUser_like_num(int user_like_num) {
        this.user_like_num = user_like_num;
    }
    public int getUser_dislike_num() {
        return user_dislike_num;
    }
    public void setUser_dislike_num(int user_dislike_num) {
        this.user_dislike_num = user_dislike_num;
    }
    public int getUser_comment_num() {
        return user_comment_num;
    }
    public void setUser_comment_num(int user_comment_num) {
        this.user_comment_num = user_comment_num;
    }
    public int getUser_collect_num() {
        return user_collect_num;
    }
    public void setUser_collect_num(int user_collect_num) {
        this.user_collect_num = user_collect_num;
    }
    public String getUser_register_date() {
        return user_register_date;
    }
    public void setUser_register_date(String user_register_date) {
        this.user_register_date = user_register_date;
    }
    @Override
    public String toString() {
        return "UsreInfo [user_id=" + user_id + ", user_nick_name="
                + user_nick_name + ", user_password=" + user_password
                + ", user_phone=" + user_phone + ", user_icon_url=" + user_icon_url
                + ", user_signature=" + user_signature + ", user_like_num="
                + user_like_num + ", user_dislike_num=" + user_dislike_num
                + ", user_comment_num=" + user_comment_num + ", user_collect_num="
                + user_collect_num + ", user_register_date=" + user_register_date
                + "]";
    }
}
