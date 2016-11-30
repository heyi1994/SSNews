package com.heyi.UniversityNews.JavaBean;

/**
 * Created by heyi on 2016/11/29.
 */

public class SmsResponse {
    public Info alibaba_aliqin_fc_sms_num_send_response;
    public class Info{
        public String request_id;
        public DetailInfo result;
        public class DetailInfo{
          public String err_code;
            public String model;
            public boolean success;
        }
    }
}
