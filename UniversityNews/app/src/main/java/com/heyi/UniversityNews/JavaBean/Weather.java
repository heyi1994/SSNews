package com.heyi.UniversityNews.JavaBean;

import java.util.ArrayList;

/**
 * Created by heyi on 2016/11/26.
 */

public class Weather {
    public String msg;
    public ArrayList<TodayInfo> result;
    public String retCode;
    public class TodayInfo{
        public String airCondition;
        public String city;
        public String coldIndex;
        public String date;
        public String distrct;
        public String dressingIndex;
        public String exerciseIndex;
        public ArrayList<FutureInfo> future;
        public String humidity;
        public String pollutionIndex;
        public String province;
        public String sunrise;
        public String sunset;
        public String temperature;
        public String time;
        public String updateTime;
        public String washIndex;
        public String weather;
        public String week;
        public String wind;
        public class FutureInfo{
            public String date;
            public String night;
            public String dayTime;
            public String temperature;
            public String week;
            public String wind;
        }
    }
}
