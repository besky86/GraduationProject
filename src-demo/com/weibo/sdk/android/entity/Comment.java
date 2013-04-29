package com.weibo.sdk.android.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Comment {

	public static final String TAG = "Comment";
	// 评论创建时间
	String created_at;
	// 评论的ID
	long id;
	// 评论的内容
	String text;
	// 评论的来源
	String source;
	// 评论作者的用户信息字段
	User user;
	// 评论的MID
	String mid;
	// 字符串型的评论ID
	String idstr;
	// 评论的微博信息字段
	Status status;
	// 评论来源评论,当评论属于对另一评论的回复时返回此字段
	Comment reply_comment;
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(String created_at, long id, String text, String source,
			User user, String mid, String idstr, Status status,
			Comment reply_comment) {
		super();
		this.created_at = created_at;
		this.id = id;
		this.text = text;
		this.source = source;
		this.user = user;
		this.mid = mid;
		this.idstr = idstr;
		this.status = status;
		this.reply_comment = reply_comment;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getIdstr() {
		return idstr;
	}
	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Comment getReply_comment() {
		return reply_comment;
	}
	public void setReply_comment(Comment reply_comment) {
		this.reply_comment = reply_comment;
	}

	public static List<Comment> getCommentsList(String response) {
		Log.v(TAG, "getCommentsList start");
		JSONArray jsonArray;
		ArrayList<Comment> list = new ArrayList<Comment>();
		try {
			jsonArray = new JSONObject(response).getJSONArray("comments");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				Comment comment = Comment.getCommentByJSON(jsonObject);

				list.add(comment);
				Log.v(TAG, jsonObject.toString());
			}

		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.v(TAG, "getCommentList  end");
		return list;
	}

	/**
	 * 
	 * getCommentByJSON 通过JSON对象获取Comment实例
	 * 
	 * @param object
	 *            JSONObject
	 * @return Comment
	 * @exception
	 * @since 1.0.0
	 */
	public static Comment getCommentByJSON(JSONObject object) {
		if (object == null)
			return null;
		Comment comment = new Comment();
		try {
			comment.setId(object.getLong("id"));

			comment.setText(object.getString("text"));
			comment.setSource(object.getString("source"));
			comment.setUser(User.getUserByJSON(object.getJSONObject("User")));
			comment.setMid(object.getString("mid"));
			comment.setIdstr(object.getString("idstr"));
			comment.setStatus(Status.getStatusByJSON(object
					.getJSONObject("status")));
			if (object.has("reply_comment")) {
				comment.setReply_comment(Comment.getCommentByJSON(object
						.getJSONObject("reply_comment")));
			}

		}
		catch (JSONException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return comment;

	}

}
