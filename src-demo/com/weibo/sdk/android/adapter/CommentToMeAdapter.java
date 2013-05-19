/**
 * CommentListAdapter.java
 * com.weibo.sdk.android.adapter
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-5-15 		Lei
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */
/**
 * 系统项目名称
 * com.weibo.sdk.android.adapter
 * CommentListAdapter.java
 * 
 * 2013-5-15
 */

package com.weibo.sdk.android.adapter;

import java.util.List;

import com.weibo.sdk.android.adapter.WeiboAdapter.WeiboHolder;
import com.weibo.sdk.android.demo.R;
import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.util.AsynImageLoader;
import com.weibo.sdk.android.util.ImageCallback;
import com.weibo.sdk.android.util.StringUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * ClassName:CommentListAdapter
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Lei
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-15
 */
/**
 * CommentListAdapter 2013-5-15 上午10:45:30
 * 
 * @version 1.0.0
 * 
 */
public class CommentToMeAdapter extends BaseAdapter {

	public List<Comment> comments;

	private Context context;

	private LayoutInflater mInflater;

	public CommentToMeAdapter(Context context, List<Comment> comments) {

		this.comments = comments;
		mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return comments == null ? 0 : comments.size();

	}

	@Override
	public Object getItem(int position) {

		// TODO Auto-generated method stub
		return comments == null ? null : comments.get(position);

	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return comments.get(position).getId();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO Auto-generated method stub
		return createViewFromResource(position, convertView);

	}

	/**
	 * createViewFromResource(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param position
	 * @param convertView
	 * @return View
	 * @exception
	 * @since 1.0.0
	 */
	private View createViewFromResource(int position, View convertView) {
		View v;
		v = mInflater.inflate(R.layout.comment_to_me_item_template, null);

		bindView(position, v);

		return v;
	}
	/**
	 * bindView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param position
	 * @param v
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void bindView(int position, View view) {

		// TODO Auto-generated method stub
		CommentHolder holder = new CommentHolder();

		holder.user_head = (ImageView) view.findViewById(R.id.img_wb_item_head);
		holder.tv_username = (TextView) view
				.findViewById(R.id.txt_comment_item_username);
		holder.tv_createdtime = (TextView) view
				.findViewById(R.id.txt_comment_item_time);
		holder.tv_comment = (TextView) view.findViewById(R.id.comment_content);
		holder.tv_source = (TextView) view.findViewById(R.id.source_content);
		Comment comment = comments.get(position);
		if (comment == null)
			return;

		Drawable image = AsynImageLoader.loadDrawable(comment.getUser()
				.getProfile_image_url(), holder.user_head, new ImageCallback() {

			@Override
			public void imageSet(Drawable drawable, ImageView imageView) {

				// TODO Auto-generated method stub
				imageView.setImageDrawable(drawable);
			}
		});

		if (image != null) {
			holder.user_head.setImageDrawable(image);
		}
		// Log.v("comment", comment.getIdstr());
		if (!StringUtil.isEmpty(comment.getCreated_at()))
			holder.tv_createdtime.setText(comment.getCreated_at());

		holder.tv_comment.setText(StringUtil.getSpannableString(comment
				.getText()));

		if (!StringUtil.isEmpty(comment.getUser().getScreen_name()))
			holder.tv_username.setText(comment.getUser().getScreen_name());

		if (comment.getReply_comment() != null) {
			if (!StringUtil.isEmpty(comment.getReply_comment().getText())) {
				holder.tv_source.setText(StringUtil
						.getSpannableString("回复我的评论: "
								+ comment.getReply_comment().getText()));

			}
		}
		else {
			holder.tv_source
					.setText("回复我的微博: " + comment.getStatus().getText());

		}

	}
	static class CommentHolder {
		ImageView user_head;
		TextView tv_username;
		TextView tv_createdtime;
		TextView tv_comment;
		TextView tv_source;

	}

}
