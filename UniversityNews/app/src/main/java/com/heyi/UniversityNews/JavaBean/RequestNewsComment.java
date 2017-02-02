package com.heyi.UniversityNews.JavaBean;

/**
 * Created by heyi on 2017/1/1.
 */

public class RequestNewsComment {
    private String API_ID;
    private int news_id;
    private int news_from;
    private int operate;
    private int good;
    private int bad;
    private int edit;
    private String edit_to;
    private String edit_info;
    private String comment_info;
    private String phone;
    private int get_times;

    private int news_good;
    private int news_bad;
    private int news_collect;

    public int getNews_good() {
        return news_good;
    }

    public void setNews_good(int news_good) {
        this.news_good = news_good;
    }

    public int getNews_bad() {
        return news_bad;
    }

    public void setNews_bad(int news_bad) {
        this.news_bad = news_bad;
    }

    public int getNews_collect() {
        return news_collect;
    }

    public void setNews_collect(int news_collect) {
        this.news_collect = news_collect;
    }

    public String getAPI_ID() {
        return API_ID;
    }

    public void setAPI_ID(String API_ID) {
        this.API_ID = API_ID;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public int getNews_from() {
        return news_from;
    }

    public void setNews_from(int news_from) {
        this.news_from = news_from;
    }

    public int getOperate() {
        return operate;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public int getEdit() {
        return edit;
    }

    public void setEdit(int edit) {
        this.edit = edit;
    }

    public String getEdit_to() {
        return edit_to;
    }

    public void setEdit_to(String edit_to) {
        this.edit_to = edit_to;
    }

    public String getComment_info() {
        return comment_info;
    }

    public void setComment_info(String comment_info) {
        this.comment_info = comment_info;
    }

    public String getEdit_info() {
        return edit_info;
    }

    public void setEdit_info(String edit_info) {
        this.edit_info = edit_info;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGet_times() {
        return get_times;
    }

    public void setGet_times(int get_times) {
        this.get_times = get_times;
    }
}
