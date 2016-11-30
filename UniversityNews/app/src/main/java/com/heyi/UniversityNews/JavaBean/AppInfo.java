package com.heyi.UniversityNews.JavaBean;

import java.io.Serializable;

/**
 * Created by heyi on 2016/11/23.
 */

public class AppInfo implements Serializable {
    private String AppName;
    private String AppVersion;
    private String AppDes;
    private String AppUpInfo;
    private String AppDownlodUrl;
    private String AppSplashUrl;

    public String getAppSplashUrl() {
        return AppSplashUrl;
    }

    public void setAppSplashUrl(String appSplashUrl) {
        AppSplashUrl = appSplashUrl;
    }

    public String getAppName() {
        return AppName;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppDes() {
        return AppDes;
    }

    public void setAppDes(String appDes) {
        AppDes = appDes;
    }

    public String getAppUpInfo() {
        return AppUpInfo;
    }

    public void setAppUpInfo(String appUpInfo) {
        AppUpInfo = appUpInfo;
    }

    public String getAppDownlodUrl() {
        return AppDownlodUrl;
    }

    public void setAppDownlodUrl(String appDownlodUrl) {
        AppDownlodUrl = appDownlodUrl;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "AppName='" + AppName + '\'' +
                ", AppVersion='" + AppVersion + '\'' +
                ", AppDes='" + AppDes + '\'' +
                ", AppUpInfo='" + AppUpInfo + '\'' +
                ", AppDownlodUrl='" + AppDownlodUrl + '\'' +
                ", AppSplashUrl='" + AppSplashUrl + '\'' +
                '}';
    }
}
