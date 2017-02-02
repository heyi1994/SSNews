package com.heyi.UniversityNews.JavaBean;

import java.io.Serializable;

/**
 * Created by heyi on 2017/1/7.
 */

public class NewsComment implements Serializable {
    private int comment_id;
    private String comment_user_icon_url;
    private String comment_user_nick_name;
    private String comment_text;
    private String comment_time;
    private int comment_good;
    private int comment_bad;
    private int comment_edit;
    private int comment_from;//模块：0 头条
    private String user_phone;
    private int comment_news_id;
    public int getComment_news_id() {
        return comment_news_id;
    }
    public void setComment_news_id(int comment_news_id) {
        this.comment_news_id = comment_news_id;
    }
    public int getComment_id() {
        return comment_id;
    }
    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }
    public String getComment_user_icon_url() {
        return comment_user_icon_url;
    }
    public void setComment_user_icon_url(String comment_user_icon_url) {
        this.comment_user_icon_url = comment_user_icon_url;
    }
    public String getComment_user_nick_name() {
        return comment_user_nick_name;
    }
    public void setComment_user_nick_name(String comment_user_nick_name) {
        this.comment_user_nick_name = comment_user_nick_name;
    }
    public String getComment_text() {
        return comment_text;
    }
    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }
    public String getComment_time() {
        return comment_time;
    }
    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }
    public int getComment_good() {
        return comment_good;
    }
    public void setComment_good(int comment_good) {
        this.comment_good = comment_good;
    }
    public int getComment_bad() {
        return comment_bad;
    }
    public void setComment_bad(int comment_bad) {
        this.comment_bad = comment_bad;
    }
    public int getComment_edit() {
        return comment_edit;
    }
    public void setComment_edit(int comment_edit) {
        this.comment_edit = comment_edit;
    }
    public int getComment_from() {
        return comment_from;
    }
    public void setComment_from(int comment_from) {
        this.comment_from = comment_from;
    }
    public String getUser_phone() {
        return user_phone;
    }
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}
