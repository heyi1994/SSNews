package com.heyi.UniversityNews.JavaBean;

import java.util.ArrayList;

/**
 * Created by heyi on 2016/11/27.
 */

public class History {
    public String msg;
    public ArrayList<Today> result;
    public String retCode;
    public class Today{
       public String date;
        public int day;
        public String event;
        public String id;
        public int month;
        public String title;

    }
}
