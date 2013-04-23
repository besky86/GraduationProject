package com.weibo.sdk.android.entity;

public class URLShort {

    //短链接
    String url_short;
    //原始长链接
    String url_long;
    //链接的类型，0：普通网页、1：视频、2：音乐、3：活动、5:：投票
    int type;
    //短链的可用状态，true为可用
    boolean result;
    public URLShort() {
        super();
        // TODO Auto-generated constructor stub
    }
    public URLShort(String url_short, String url_long, int type, boolean result) {
        super();
        this.url_short = url_short;
        this.url_long = url_long;
        this.type = type;
        this.result = result;
    }
    public String getUrl_short() {
        return url_short;
    }
    public void setUrl_short(String url_short) {
        this.url_short = url_short;
    }
    public String getUrl_long() {
        return url_long;
    }
    public void setUrl_long(String url_long) {
        this.url_long = url_long;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    
    
}
