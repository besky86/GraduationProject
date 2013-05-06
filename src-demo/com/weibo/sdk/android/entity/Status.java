package com.weibo.sdk.android.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.weibo.sdk.android.WeiboException;

import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.demo.MainTabActivity;
import com.weibo.sdk.android.demo.TestActivity;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.util.StringUtil;

public class Status implements Serializable {

	/**
	 * serialVersionUID:TODO
	 * 
	 * @since 1.0.0
	 */

	private static final long serialVersionUID = 1L;

	public final static String TAG = "Status";

	String uid;
	// 创建时间
	String created_at;
	// 微博ID
	long id;
	// 微博MID
	long mid;
	// 字符串型的微博ID
	String idstr;
	// 微博信息内容
	String text;
	// 微博来源
	String source;
	// 是否已收藏
	boolean favorited;
	// 是否被截断
	boolean truncated;
	// 回复ID（暂未支持）
	String in_reply_to_status_id;
	// 回复人UID（暂未支持）
	String in_reply_to_user_id;
	// 回复人昵称（暂未支持）
	String in_reply_to_screen_name;
	// 缩略图片地址，没有时不返回此字段
	String thumbnail_pic;
	// 中等尺寸图片地址。没有时不返回
	String bmiddle_pic;
	// 原始图片地址。没有时不返回
	String original_pic;
	// 地理信息字段
	Geo geo;
	// 微博作者的用户信息字段
	User user;
	// 被转发的原微博信息字段，当该微博为转发微博时返回
	Status retweeted_status;
	// 转发数
	int reposts_count;
	// 评论数
	int comments_count;
	// 表态数
	int attitudes_count;

	int mlevel;

	Visible visible;

	public Status() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Status(String created_at, long id, long mid, String idstr,
			String text, String source, boolean favorited, boolean truncated,
			String thumbnail_pic, String bmiddle_pic, String original_pic,
			Geo geo, User user, Status retweeted_status, int reposts_count,
			int comments_count, int attitudes_count, Visible visible) {
		super();
		this.created_at = created_at;
		this.id = id;
		this.mid = mid;
		this.idstr = idstr;
		this.text = text;
		this.source = source;
		this.favorited = favorited;
		this.truncated = truncated;
		this.thumbnail_pic = thumbnail_pic;
		this.bmiddle_pic = bmiddle_pic;
		this.original_pic = original_pic;
		this.geo = geo;
		this.user = user;
		this.retweeted_status = retweeted_status;
		this.reposts_count = reposts_count;
		this.comments_count = comments_count;
		this.attitudes_count = attitudes_count;
		this.visible = visible;
	}

	public static List<Status> getStatusesList(String statusesJSON) {
		Log.v(TAG, "getStatusedList start");
		JSONArray jsonArray;
		ArrayList<Status> list = new ArrayList<Status>();
		try {
			jsonArray = new JSONObject(statusesJSON).getJSONArray("statuses");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				Status status = Status.getStatusByJSON(jsonObject);

				list.add(status);
				Log.v(TAG, jsonObject.toString());
			}

		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.v(TAG, "getStatusList  end");
		return list;

	}

	/**
	 * getStatusByJSON(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param jsonObject
	 * @return Status
	 * @exception
	 * @since 1.0.0
	 */
	public static Status getStatusByJSON(JSONObject jsonObject) {

		Log.v(TAG, "getStatusByJSON start");
		// TODO Auto-generated method stub
		Status status = new Status();
		/*
		 * 获取相应字段
		 */

		try {
			String created_at = jsonObject.getString("created_at");
			// 创建时间
			long id = jsonObject.getLong("id"); // 微博ID
			long mid = jsonObject.getLong("mid"); // 微博MID
			String idstr = jsonObject.getString("idstr");// 字符串型的微博ID
			String text = jsonObject.getString("text");// 微博信息内容
			String source = jsonObject.getString("source");// 微博来源
			boolean favorited = jsonObject.getBoolean("favorited");// 是否已收藏
			boolean truncated = jsonObject.getBoolean("truncated");

			if (jsonObject.has("thumbnail_pic")) {

				status.setThumbnail_pic(jsonObject.getString("thumbnail_pic"));
			}
			if (jsonObject.has("bmiddle_pic")) {
				status.setBmiddle_pic(jsonObject.getString("bmiddle_pic"));
			}
			if (jsonObject.has("original_pic")) {
				status.setBmiddle_pic(jsonObject.getString("original_pic"));
			}

			// Delete by Lei@2013/04/29 DEL START
			// if (!jsonObject.isNull("geo")) {
			// status.setGeo(Geo.getGeoByJSON(jsonObject.getJSONObject("geo")));
			// }
			// Delete by Lei@2013/04/29 DEL END
			if (!jsonObject.isNull("user")) {
				status.setUser(User.getUserByJSON(jsonObject
						.getJSONObject("user")));
			}
			if (!jsonObject.isNull("uid")) {
				status.setUid(jsonObject.getString("uid"));

			}

			if (jsonObject.has("retweeted_status")) {
				Status retweeted_status = Status.getStatusByJSON(jsonObject
						.getJSONObject("retweeted_status"));
				status.setRetweeted_status(retweeted_status);
			}

			int reposts_count = jsonObject.getInt("reposts_count");
			int comments_count = jsonObject.getInt("comments_count");
			int attitudes_count = jsonObject.getInt("attitudes_count");

			JSONObject visi = jsonObject.getJSONObject("visible");
			Visible visible = new Visible();
			visible.setList_id(visi.getInt("list_id"));
			visible.setType(visi.getInt("type"));

			status.setCreated_at(created_at);
			status.setId(id);
			status.setMid(mid);
			status.setIdstr(idstr);
			status.setText(text);
			status.setSource(source);
			status.setFavorited(favorited);
			status.setTruncated(truncated);

			status.setReposts_count(reposts_count);
			status.setComments_count(comments_count);
			status.setAttitudes_count(attitudes_count);
			status.setVisible(visible);

		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.v(TAG, "JSON Exception");
			e.printStackTrace();
		}
		Log.v(TAG, "getStatusByJSON end");
		return status;
	}
	public Status(String created_at, long id, long mid, String idstr,
			String text, String source, boolean favorited, boolean truncated,
			String in_reply_to_status_id, String in_reply_to_user_id,
			String in_reply_to_screen_name, String thumbnail_pic,
			String bmiddle_pic, String original_pic, Geo geo, User user,
			Status retweeted_status, int reposts_count, int comments_count,
			int attitudes_count, int mleve, Visible visible) {
		super();
		this.created_at = created_at;
		this.id = id;
		this.mid = mid;
		this.idstr = idstr;
		this.text = text;
		this.source = source;
		this.favorited = favorited;
		this.truncated = truncated;
		this.in_reply_to_status_id = in_reply_to_status_id;
		this.in_reply_to_user_id = in_reply_to_user_id;
		this.in_reply_to_screen_name = in_reply_to_screen_name;
		this.thumbnail_pic = thumbnail_pic;
		this.bmiddle_pic = bmiddle_pic;
		this.original_pic = original_pic;
		this.geo = geo;
		this.user = user;
		this.retweeted_status = retweeted_status;
		this.reposts_count = reposts_count;
		this.comments_count = comments_count;
		this.attitudes_count = attitudes_count;
		this.mlevel = mleve;
		this.visible = visible;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * uid
	 * 
	 * @return the uid
	 * @since 1.0.0
	 */

	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public String getIdstr() {
		return idstr;
	}

	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	public String getIn_reply_to_status_id() {
		return in_reply_to_status_id;
	}

	public void setIn_reply_to_status_id(String in_reply_to_status_id) {
		this.in_reply_to_status_id = in_reply_to_status_id;
	}

	public String getIn_reply_to_user_id() {
		return in_reply_to_user_id;
	}

	public void setIn_reply_to_user_id(String in_reply_to_user_id) {
		this.in_reply_to_user_id = in_reply_to_user_id;
	}

	public String getIn_reply_to_screen_name() {
		return in_reply_to_screen_name;
	}

	public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
		this.in_reply_to_screen_name = in_reply_to_screen_name;
	}

	public String getThumbnail_pic() {
		return thumbnail_pic;
	}

	public void setThumbnail_pic(String thumbnail_pic) {
		this.thumbnail_pic = thumbnail_pic;
	}

	public String getBmiddle_pic() {
		return bmiddle_pic;
	}

	public void setBmiddle_pic(String bmiddle_pic) {
		this.bmiddle_pic = bmiddle_pic;
	}

	public String getOriginal_pic() {
		return original_pic;
	}

	public void setOriginal_pic(String original_pic) {
		this.original_pic = original_pic;
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getRetweeted_status() {
		return retweeted_status;
	}

	public void setRetweeted_status(Status retweeted_status) {
		this.retweeted_status = retweeted_status;
	}

	public int getReposts_count() {
		return reposts_count;
	}

	public void setReposts_count(int reposts_count) {
		this.reposts_count = reposts_count;
	}

	public int getComments_count() {
		return comments_count;
	}

	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}

	public int getAttitudes_count() {
		return attitudes_count;
	}

	public void setAttitudes_count(int attitudes_count) {
		this.attitudes_count = attitudes_count;
	}

	public int getMlevel() {
		return mlevel;
	}

	public void setMlevel(int mleve) {
		this.mlevel = mleve;
	}

	public Visible getVisible() {
		return visible;
	}

	public void setVisible(Visible visible) {
		this.visible = visible;
	}

}

class Visible {
	int type; // type取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博
	int list_id; // 分组的组号

	public Visible() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Visible(int type, int list_id) {
		super();
		this.type = type;
		this.list_id = list_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getList_id() {
		return list_id;
	}

	public void setList_id(int list_id) {
		this.list_id = list_id;
	}

}

class UsersRequestListener implements RequestListener {

	private static final String TAG = "UserRequestListener";

	Status s;

	@Override
	public void onComplete(String response) {
		// TODO Auto-generated method stub

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(response);
			User user = User.getUserByJSON(jsonObject);
			s.setUser(user);
		}
		catch (JSONException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	@Override
	public void onIOException(IOException e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(WeiboException e) {
		// TODO Auto-generated method stub

	}

}
