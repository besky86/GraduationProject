package com.weibo.sdk.android.adapter;

import java.util.List;

import com.weibo.sdk.android.api.FriendshipsAPI;
import com.weibo.sdk.android.demo.MainTabActivity;
import com.weibo.sdk.android.demo.R;
import com.weibo.sdk.android.entity.User;
import com.weibo.sdk.android.requestlisteners.NullRequestListener;
import com.weibo.sdk.android.util.AsynImageLoader;
import com.weibo.sdk.android.util.ImageCallback;
import com.weibo.sdk.android.util.StringUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

public class UserListAdapter extends BaseAdapter {

	private Context context;
	private List<User> users;
	// private User user;
	private LayoutInflater mInflater;
	/**
	 * users
	 * 
	 * @return the users
	 * @since 1.0.0
	 */

	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserListAdapter(Context context, List<User> users) {
		this.context = context;
		this.users = users;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return users == null ? 0 : users.size();

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return users == null ? null : users.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return users.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return createViewFromResource(position, convertView);

	}

	private View createViewFromResource(int position, View convertView) {

		// TODO Auto-generated method stub
		View v;
		v = mInflater.inflate(R.layout.user_item_template, null);

		bindView(position, v);

		return v;

	}

	private void bindView(int position, View v) {

		// TODO Auto-generated method stub
		final UserHolder holder = new UserHolder();
		holder.user_name = (TextView) v.findViewById(R.id.tv_username);
		holder.user_head = (ImageView) v.findViewById(R.id.img_wb_item_head);
		holder.status_text = (TextView) v.findViewById(R.id.tv_status);
		holder.follow = (Button) v.findViewById(R.id.btn_follow);

		final User user = users.get(position);
		// Log.v("USER", user.getStatus().getText());
		holder.user_name.setText(user.getScreen_name());

		Drawable image = AsynImageLoader.loadDrawable(
				user.getProfile_image_url(), holder.user_head,
				new ImageCallback() {

					@Override
					public void imageSet(Drawable drawable, ImageView imageView) {

						imageView.setImageDrawable(drawable);
					}
				});

		if (image != null) {
			holder.user_head.setImageDrawable(image);
		}
		// Delete by Lei@2013/05/18 DEL START
		if (user.getStatus() != null
				&& !StringUtil.isEmpty(user.getStatus().getText()))

			holder.status_text.setText(user.getStatus().getText());

		if (user.isFollowing()) {
			holder.follow.setBackgroundResource(R.drawable.btn_unfollow_shape);
			holder.follow.setText(R.string.unfollow);
		}
		if (!user.getIdstr().equals(MainTabActivity.accessToken.getUid()))
			holder.follow.setVisibility(View.VISIBLE);

		holder.follow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				if (holder.follow.getText().toString().equals("关注")) {

					new FriendshipsAPI(MainTabActivity.accessToken).create(
							user.getId(), user.getScreen_name(),
							new NullRequestListener());

					holder.follow
							.setBackgroundResource(R.drawable.btn_unfollow_shape);
					holder.follow.setText(R.string.unfollow);

				}
				else {

					new FriendshipsAPI(MainTabActivity.accessToken).destroy(
							user.getId(), null, new NullRequestListener());
					holder.follow
							.setBackgroundResource(R.drawable.btn_follow_shape);
					holder.follow.setText(R.string.follow);
				}

			}

		});

	}
	static class UserHolder {
		ImageView user_head;
		TextView user_name;
		TextView status_text;
		Button follow;

	}

}
