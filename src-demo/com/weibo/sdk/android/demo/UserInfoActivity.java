package com.weibo.sdk.android.demo;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.FriendshipsAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.entity.User;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.requestlisteners.GetUserRequestListener;
import com.weibo.sdk.android.requestlisteners.NullRequestListener;
import com.weibo.sdk.android.util.AsynImageLoader;
import com.weibo.sdk.android.util.ImageCallback;
import com.weibo.sdk.android.util.StringUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;

public class UserInfoActivity extends Activity {
	private long userId = 0;

	private Button btn_back;
	private Button btn_home;
	private ImageView user_head;
	private Button btn_follow;
	private LinearLayout followLayout;
	private LinearLayout weiboLayout;
	private LinearLayout followerLayout;
	private LinearLayout favLayout;
	private TextView user_name;
	private TextView tv_location;
	private TextView tv_description;
	private TextView num_follow;
	private TextView num_weibo;
	private TextView num_follower;
	private TextView num_fav;

	private Drawable image_head;
	private User user;

	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			try {
				user = User.getUserByJSON(new JSONObject(msg.getData()
						.getString("user")));
			}
			catch (JSONException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
				Util.showToast(UserInfoActivity.this, "数据解析异常");
			}

			if (user != null) {

				Drawable image = AsynImageLoader.loadDrawable(
						user.getProfile_image_url(), user_head,
						new ImageCallback() {

							@Override
							public void imageSet(Drawable drawable,
									ImageView imageView) {
								imageView.setImageDrawable(drawable);
							}
						});

				if (image != null) {
					image_head = image;
					user_head.setImageDrawable(image);
				}
				user_name.setText(user.getScreen_name());

				String location = user.getLocation();
				if (StringUtil.isEmpty(location)) {
					tv_location.setText("其他");

				}
				// Log.v("userinfo",location);
				else
					tv_location.setText(location);

				String description = user.getDescriprtion();
				if (StringUtil.isEmpty(description)) {
					tv_description.setText("该用户未设置任何个人说明");
				}
				else
					tv_description.setText(description);

				num_follow.setText("" + user.getFriends_count());
				num_follower.setText("" + user.getFollowers_count());
				num_weibo.setText("" + user.getStatuses_count());

				if (user.isFollowing()) {
					btn_follow
							.setBackgroundResource(R.drawable.btn_unfollow_shape);
					btn_follow.setText(R.string.unfollow);
				}
				btn_follow.setVisibility(View.VISIBLE);

				num_fav.setText("" + user.getFavourites_count());

			}

		}

	};

	/**
	 * 创建一个新的实例 UserInfoActivity.
	 * 
	 */
	public UserInfoActivity() {

		super();
		// TODO Auto-generated constructor stub

	}

	/**
	 * 创建一个新的实例 UserInfoActivity.
	 * 
	 * @param userId
	 */
	public UserInfoActivity(long userId) {
		super();
		this.userId = userId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		if (userId == 0) {
			userId = getIntent().getLongExtra("user_id", 0l);
		}

		getViews();

		Log.v("toke你", MainTabActivity.accessToken.getToken());
		new UsersAPI(MainTabActivity.accessToken).show(userId,
				new GetUserRequestListenerIn());
		setListeners();
	}

	private void getViews() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_home = (Button) findViewById(R.id.btn_home);
		user_name = (TextView) findViewById(R.id.user_name);
		user_head = (ImageView) findViewById(R.id.user_head);
		btn_follow = (Button) findViewById(R.id.btn_follow);
		followLayout = (LinearLayout) findViewById(R.id.follow_layout);;
		weiboLayout = (LinearLayout) findViewById(R.id.weibo_layout);
		followerLayout = (LinearLayout) findViewById(R.id.follower_layout);
		favLayout = (LinearLayout) findViewById(R.id.fav_layout);

		tv_location = (TextView) findViewById(R.id.tv_location);
		tv_description = (TextView) findViewById(R.id.tv_despription);
		num_follow = (TextView) findViewById(R.id.num_follow);
		num_weibo = (TextView) findViewById(R.id.num_weibo);
		num_follower = (TextView) findViewById(R.id.num_follower);

		num_fav = (TextView) findViewById(R.id.num_fav);

	}

	private void setListeners() {

		followLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(UserInfoActivity.this,
						FollowingActivity.class);
				intent.putExtra("user_id", user.getId());
				intent.putExtra("following_num", user.getFriends_count());
				startActivity(intent);

			}

		});

		followerLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(UserInfoActivity.this,
						FollowerListActivity.class);
				intent.putExtra("user_id", user.getId());
				intent.putExtra("follower_num", user.getFollowers_count());
				startActivity(intent);

			}

		});

		btn_follow.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				if (btn_follow.getText().toString().equals("关注")) {

					new FriendshipsAPI(MainTabActivity.accessToken).create(
							userId, user.getScreen_name(),
							new NullRequestListener());

					btn_follow
							.setBackgroundResource(R.drawable.btn_unfollow_shape);
					btn_follow.setText(R.string.unfollow);

				}
				else {

					new FriendshipsAPI(MainTabActivity.accessToken).destroy(
							userId, null, new NullRequestListener());
					btn_follow
							.setBackgroundResource(R.drawable.btn_follow_shape);
					btn_follow.setText(R.string.follow);
				}

			}

		});

		weiboLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (user != null && user.getId() > 0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(UserInfoActivity.this,
							UserWeiboActivity.class);
					intent.putExtra("user_id", user.getId());
					// intent.putExtra("follower_num",
					// user.getFollowers_count());
					startActivity(intent);

				}
			}

		});

	}

	class GetUserRequestListenerIn implements RequestListener {

		@Override
		public void onComplete(String response) {

			Message msg = new Message();
			msg.getData().putString("user", response);
			UserInfoActivity.this.h.sendMessage(msg);

		}
		@Override
		public void onIOException(IOException e) {

			// TODO Auto-generated method stub
			// Util.showToast(UserInfoActivity.this, "操作异常");
			Toast toast = Toast.makeText(UserInfoActivity.this, "操作异常",
					Toast.LENGTH_SHORT);
			toast.show();
		}

		@Override
		public void onError(WeiboException e) {

			// TODO Auto-generated method stub
			Util.showToast(UserInfoActivity.this, "操作失败");

		}

	}

}
