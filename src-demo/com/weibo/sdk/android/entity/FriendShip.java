/**
 * FriendShip.java
 * com.weibo.sdk.android.entity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-5-17 		Lei
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
*/
/**
 * 系统项目名称
 * com.weibo.sdk.android.entity
 * FriendShip.java
 * 
 * 2013-5-17
 */

package com.weibo.sdk.android.entity;
/**
 * ClassName:FriendShip
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-17
 */
/**
 * FriendShip
 * 2013-5-17 下午2:13:51
 * 
 * @version 1.0.0
 * 
 */
public class FriendShip {
	
	private boolean followed_by; //是否被关注
	private boolean following; //是否关注对方
	private long user_id;
	private boolean notifications_enabled;
	private String screen_name;
	
	
	
	
	
	/**
	 * 创建一个新的实例 FriendShip.
	 *
	 */
	public FriendShip() {
		
		super();
		// TODO Auto-generated constructor stub
		
	}
	/**
	 * 创建一个新的实例 FriendShip.
	 *
	 * @param followed_by
	 * @param following
	 * @param user_id
	 * @param notifications_enabled
	 * @param screen_name
	 */
	public FriendShip(boolean followed_by, boolean following, long user_id,
			boolean notifications_enabled, String screen_name) {
		super();
		this.followed_by = followed_by;
		this.following = following;
		this.user_id = user_id;
		this.notifications_enabled = notifications_enabled;
		this.screen_name = screen_name;
	}
	/**
	 * followed_by
	 *
	 * @return  the followed_by
	 * @since   1.0.0
	 */
	
	public boolean isFollowed_by() {
		return followed_by;
	}
	/**
	 * @param followed_by the followed_by to set
	 */
	public void setFollowed_by(boolean followed_by) {
		this.followed_by = followed_by;
	}
	/**
	 * following
	 *
	 * @return  the following
	 * @since   1.0.0
	 */
	
	public boolean isFollowing() {
		return following;
	}
	/**
	 * @param following the following to set
	 */
	public void setFollowing(boolean following) {
		this.following = following;
	}
	/**
	 * user_id
	 *
	 * @return  the user_id
	 * @since   1.0.0
	 */
	
	public long getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	/**
	 * notifications_enabled
	 *
	 * @return  the notifications_enabled
	 * @since   1.0.0
	 */
	
	public boolean isNotifications_enabled() {
		return notifications_enabled;
	}
	/**
	 * @param notifications_enabled the notifications_enabled to set
	 */
	public void setNotifications_enabled(boolean notifications_enabled) {
		this.notifications_enabled = notifications_enabled;
	}
	/**
	 * screen_name
	 *
	 * @return  the screen_name
	 * @since   1.0.0
	 */
	
	public String getScreen_name() {
		return screen_name;
	}
	/**
	 * @param screen_name the screen_name to set
	 */
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	
	


}

