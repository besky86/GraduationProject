/**
 * Trends.java
 * com.weibo.sdk.android.entity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-5-18 		Lei
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */
/**
 * 系统项目名称
 * com.weibo.sdk.android.entity
 * Trends.java
 * 
 * 2013-5-18
 */

package com.weibo.sdk.android.entity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ClassName:Trends
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-18
 */
/**
 * Trends 2013-5-18 下午10:06:53
 * 
 * @version 1.0.0
 * 
 */
public class Trends {

	long num;
	String hotword;

	long id;

	/**
	 * 创建一个新的实例 Trends.
	 * 
	 */
	public Trends() {

		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * 创建一个新的实例 Trends.
	 * 
	 * @param num
	 * @param hotword
	 * @param id
	 */
	public Trends(long num, String hotword, long id) {
		super();
		this.num = num;
		this.hotword = hotword;
		this.id = id;
	}

	public Trends getTrendsByJSON(JSONObject json) {
		Trends trends = new Trends();

		if (json != null) {
			try {
				trends.setHotword(json.getString("hotword"));
				trends.setId(json.getLong("trend_id"));
				trends.setNum(json.getLong("num"));
			}
			catch (JSONException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
		return trends;

	}
	public List<Trends> getTrendsByString(String json) {

		
		return null;

	}
	/**
	 * num
	 * 
	 * @return the num
	 * @since 1.0.0
	 */

	public long getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(long num) {
		this.num = num;
	}

	/**
	 * hotword
	 * 
	 * @return the hotword
	 * @since 1.0.0
	 */

	public String getHotword() {
		return hotword;
	}

	/**
	 * @param hotword
	 *            the hotword to set
	 */
	public void setHotword(String hotword) {
		this.hotword = hotword;
	}

	/**
	 * id
	 * 
	 * @return the id
	 * @since 1.0.0
	 */

	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

}
