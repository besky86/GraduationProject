package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.*;
import com.weibo.sdk.android.api.*;
import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.net.RequestListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.*;
/**
 * 关注界面
 * FollowingActivity
 * 2013-5-24 下午8:50:41
 * 
 * @version 1.0.0
 *
 */
@SuppressLint("HandlerLeak")
public class FollowingActivity extends Activity {

	private static final String TAG = "FollowingAvtivity";
	private Button btn_back;
	private Button btn_home;
	private ListView lv_following;

	private View moreView;
	private long userId;
	int previous = 0;
	int next = 0;

	FriendshipsAPI friendAPI = new FriendshipsAPI(MainTabActivity.accessToken);
	List<User> users = new ArrayList<User>();
	UserListAdapter adapter;

	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			List<User> usersList = User.getUsersList((msg.getData()
					.getString("response")));

			try {
				JSONObject json = new JSONObject(msg.getData().getString(
						"response"));
				previous = json.getInt("previous_cursor");
				next = json.getInt("next_cursor");
				if (previous >= next) {

					lv_following.removeFooterView(moreView);
					// moreView.setEnabled(false);

				}
				// moreView.setVisibility(View.GONE);
				Log.v("USER", "" + next);
			}
			catch (JSONException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.v("JSON", e.getMessage());

			}

			if (usersList.size() == 0) {
				Util.showToast(FollowingActivity.this, "无新的数据");
				return;
			}

			// Collections.sort(userList);
			if (adapter.getCount() == 0) {
				adapter.setUsers(usersList);
				users = usersList;
			}
			else {

				for (User user : usersList) {
					if (user == null)
						continue;

					adapter.getUsers().add(user);
				}

			}
			adapter.notifyDataSetChanged();
			Util.showToast(FollowingActivity.this, "更新完成");
			Log.v(TAG, msg.getData().getString("response"));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_following);
		userId = getIntent().getLongExtra("user_id", 0l);
		getViews();
		setListeners();
		showUsers();
	}

	private void getViews() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_home = (Button) findViewById(R.id.btn_home);
		lv_following = (ListView) findViewById(R.id.lv_following);
		adapter = new UserListAdapter(this, users);
		moreView = View.inflate(this, R.layout.footerview, null);
		lv_following.addFooterView(moreView);
		lv_following.setAdapter(adapter);
	}

	private void setListeners() {

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				FollowingActivity.this.finish();

			}

		});
		btn_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(FollowingActivity.this,
						MainTabActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}

		});
		lv_following.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub
				Log.v("listm", "begin");

				Intent intent = new Intent(FollowingActivity.this,
						UserInfoActivity.class);

				intent.putExtra("user_id", users.get(position).getId());
				// intent.putExtra(name, value)
				FollowingActivity.this.startActivity(intent);

			}

		});

		moreView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				showUsers();

			}

		});

	}

	private void showUsers() {
		friendAPI.friends(userId, 20, next, false, new UserRequestListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.following, menu);
		return true;
	}

	class UserRequestListener implements RequestListener {

		@Override
		public void onComplete(String response) {
			// TODO Auto-generated method stub
			Log.v(TAG, response);
			Message message = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("response", response);
			message.setData(bundle);
			Log.v(TAG, response);
			message.what = 1;
			FollowingActivity.this.h.sendMessage(message);
			// testView.setText(response);
		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			Util.showToast(FollowingActivity.this, "获取数据异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Util.showToast(FollowingActivity.this, "失败");

		}

	}

}
