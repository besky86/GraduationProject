/**
 * SearchUser.java
 * com.weibo.sdk.android.entity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-5-20 		Lei
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */
/**
 * 系统项目名称
 * com.weibo.sdk.android.entity
 * SearchUser.java
 * 
 * 2013-5-20
 */

package com.weibo.sdk.android.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * ClassName:SearchUser
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-20
 */
/**
 * SearchUser 2013-5-20 下午4:24:24
 * 
 * @version 1.0.0
 * 
 */
public class User2Search {
	private String screen_name;
	private String icon_url;
	private int followers_count;
	private long uid;

	/**
	 * 创建一个新的实例 SearchUser.
	 * 
	 */
	public User2Search() {

		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * 创建一个新的实例 SearchUser.
	 * 
	 * @param screen_name
	 * @param followers_count
	 * @param uid
	 */
	public User2Search(String screen_name, int followers_count, long uid) {
		super();
		this.screen_name = screen_name;
		this.followers_count = followers_count;
		this.uid = uid;
	}

	/**
	 * screen_name
	 * 
	 * @return the screen_name
	 * @since 1.0.0
	 */

	public String getScreen_name() {
		return screen_name;
	}

	/**
	 * @param screen_name
	 *            the screen_name to set
	 */
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	/**
	 * followers_count
	 * 
	 * @return the followers_count
	 * @since 1.0.0
	 */

	public int getFollowers_count() {
		return followers_count;
	}

	/**
	 * icon_url
	 * 
	 * @return the icon_url
	 * @since 1.0.0
	 */

	public String getIcon_url() {
		return icon_url;
	}

	/**
	 * @param icon_url
	 *            the icon_url to set
	 */
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}

	/**
	 * @param followers_count
	 *            the followers_count to set
	 */
	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	/**
	 * uid
	 * 
	 * @return the uid
	 * @since 1.0.0
	 */

	public long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}

	public static List<User2Search> getResultByString(String str) {
		ArrayList<User2Search> resultList = new ArrayList<User2Search>();
		try {
			JSONArray results = new JSONArray(str);
			for (int i = 0; i != results.length(); i++) {

				Log.v("%E5%95%8A", "" + results.length());
				resultList.add(getUserByJson(results.optJSONObject(i)));

			}
		}
		catch (JSONException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return resultList;

	}
	public static User2Search getUserByJson(JSONObject obj) {

		User2Search result = new User2Search();
		try {
			result.setUid(obj.getLong("uid"));

			result.setFollowers_count(obj.getInt("followers_count"));
			result.setScreen_name(obj.getString("screen_name"));
		}
		catch (JSONException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return result;
	}

}
