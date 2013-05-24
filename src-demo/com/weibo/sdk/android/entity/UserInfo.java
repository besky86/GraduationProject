package com.weibo.sdk.android.entity;

import android.graphics.drawable.Drawable;

/**
 * 2013-04-24
 * @author lei
 *
 */
public class UserInfo {

	private Long id;
	private String userId;
	private String userName;
	private String token;
	private long expiresTime;
	private Drawable userIcon;

	public static final String TB_NAME = "UserInfo";

	public static final String ID = "_id";
	public static final String USER_ID = "uid";
	public static final String USER_NAME = "userName";
	public static final String TOKEN = "token";
	public static final String EXPIRESTIME = "expiresTime";
	public static final String USERICON = "userIcon";

	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfo(String userId, String userName, String token,
			long expiresTime) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.token = token;
		this.expiresTime = expiresTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpiresTime() {
		return expiresTime;
	}

	public void setExpiresTime(long expiresTime) {
		this.expiresTime = expiresTime;
	}

	public Drawable getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(Drawable userIcon) {
		this.userIcon = userIcon;
	}

}
