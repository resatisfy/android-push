package com.resatisfy.android_lib.models;

public class RSReportModel {

    private String status;
    public String getStatus() { return this.status; }
    public void setStatus(String msg) { this.status = status; }

    private String msg;
    public String getMsg() { return this.msg; }
    public void setMsg(String msg) { this.msg = msg; }

    private String appKey;
    public String getAppKey() { return this.appKey; }
    public void setAppKey(String appKey) { this.appKey = appKey; }

    private String appSecret;
    public String getAppSecret() { return this.appSecret; }
    public void setAppSecret(String appSecret) { this.appSecret = appSecret; }

    private String deviceType;
    public String getDeviceType() { return this.deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }


    private String stackTrace;
    public String getStackTrace() { return this.stackTrace; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }


    @Override
    public String toString()
    {
        return "appKey = "+appKey+"&appSecret = "+appSecret+"&deviceType = "+deviceType+"&stackTrace = "+stackTrace;
    }

}
