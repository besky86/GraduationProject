package com.weibo.sdk.android.entity;

public class Privacy {

    // 是否可以评论我的微博，0：所有人、1：关注的人、2：可信用户
    int comment;
    // 是否开启地理信息，0：不开启、1：开启
    int geo;
    // 是否可以给我发私信，0：所有人、1：我关注的人、2：可信用户
    int message;
    // 是否可以通过真名搜索到我，0：不可以、1：可以
    int realname;
    // 勋章是否可见，0：不可见、1：可见
    int badge;
    // 是否可以通过手机号码搜索到我，0：不可以、1：可以
    int mobile;
    // 是否开启webim， 0：不开启、1：开启
    int webim;

    public Privacy() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Privacy(int comment, int geo, int message, int realname, int badge,
            int mobile, int webim) {
        super();
        this.comment = comment;
        this.geo = geo;
        this.message = message;
        this.realname = realname;
        this.badge = badge;
        this.mobile = mobile;
        this.webim = webim;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getGeo() {
        return geo;
    }

    public void setGeo(int geo) {
        this.geo = geo;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getRealname() {
        return realname;
    }

    public void setRealname(int realname) {
        this.realname = realname;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getWebim() {
        return webim;
    }

    public void setWebim(int webim) {
        this.webim = webim;
    }

}
