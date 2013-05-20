package com.weibo.sdk.android.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.WeiboAdapter.WeiboHolder;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.demo.MainTabActivity;
import com.weibo.sdk.android.demo.R;
import com.weibo.sdk.android.demo.SearchActivity;
import com.weibo.sdk.android.demo.Util;
import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.net.RequestListener;
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
public class SearchUsertAdapter extends BaseAdapter {

	public List<User2Search> users = new ArrayList<User2Search>();

	private Context context;

	private LayoutInflater mInflater;

	/**
	 * 创建一个新的实例 SearchUsertAdapter.
	 * 
	 */
	public SearchUsertAdapter() {

		super();
		// TODO Auto-generated constructor stub

	}

	public SearchUsertAdapter(Context context, List<User2Search> users) {

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
		return users.get(position).getUid();

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
		v = mInflater.inflate(R.layout.search_user_template, null);

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

		holder.tv_username = (TextView) view.findViewById(R.id.tv_username);
		holder.tv_follower_count = (TextView) view
				.findViewById(R.id.follower_num);
		User2Search user = users.get(position);
		if (user == null)
			return;

		// Delete by Lei@2013/05/20 DEL START
		// if (user.getIcon_url() == null) {
		// new UsersAPI(MainTabActivity.accessToken).show(user.getUid(),
		// new UserRequestListener(user));
		// }
		// Delete by Lei@2013/05/20 DEL END
		Log.v("usersearch", user.toString());

		if (!StringUtil.isEmpty(user.getScreen_name()))
			holder.tv_username.setText(user.getScreen_name());
		holder.tv_follower_count
				.setText("粉丝" + user.getFollowers_count() + "人");

		// Delete by Lei@2013/05/20 DEL START
		// Drawable image = AsynImageLoader.loadDrawable(user.getIcon_url(),
		// holder.user_head, new ImageCallback() {
		//
		// @Override
		// public void imageSet(Drawable drawable, ImageView imageView) {
		//
		// // TODO Auto-generated method stub
		// imageView.setImageDrawable(drawable);
		// }
		// });
		//
		// if (image != null) {
		// holder.user_head.setImageDrawable(image);
		// }
		// Delete by Lei@2013/05/20 DEL END
		// Log.v("comment", comment.getIdstr());

	}
	class UserRequestListener implements RequestListener {

		User2Search user;

		/**
		 * 创建一个新的实例 UserRequestListener.
		 * 
		 * @param user2
		 */
		public UserRequestListener(User2Search user2) {

			// TODO Auto-generated constructor stub
			this.user = user2;

		}

		@Override
		public void onComplete(String response) {
			// TODO Auto-generated method stub

			try {
				user.setIcon_url(User.getUserByJSON(new JSONObject(response))
						.getProfile_image_url());
			}
			catch (JSONException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			// testView.setText(response);
		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			// Util.showToast(SearchActivity.this, "获取数据异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			// Util.showToast(SearchActivity.this, "失败");

		}
	}
	static class CommentHolder {
		ImageView user_head;
		TextView tv_username;
		TextView tv_follower_count;

	}

}
