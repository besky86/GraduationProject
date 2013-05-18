package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.*;
import com.weibo.sdk.android.api.*;
import com.weibo.sdk.android.demo.FollowerListActivity.UserRequestListener;
import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.net.RequestListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.*;

public class FollowerListActivity extends Activity {

	private static final String TAG = "FollowerAvtivity";
	private Button btn_back;
	private Button btn_home;
	private ListView lv_follower;
	private View moreView;
	private long userId;
	int previous = 0;
	int next = 0;

	int max;
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

					lv_follower.removeFooterView(moreView);
					// moreView.setEnabled(false);

				}

				Log.v("next", "" + next);

			}
			catch (JSONException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.v("JSON", e.getMessage());

			}

			if (usersList.size() == 0) {
				Util.showToast(FollowerListActivity.this, "无新的数据");
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

					// Log.v("USER", user.getStatus().getText());
					adapter.getUsers().add(user);
				}

			}
			adapter.notifyDataSetChanged();
			if (next <= previous)
				moreView.setClickable(false);
			Util.showToast(FollowerListActivity.this, "更新完成");
			Log.v(TAG, msg.getData().getString("response"));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_follower_list);
		userId = getIntent().getLongExtra("user_id", 0l);
		max = getIntent().getIntExtra("follower_num", 0);
		getViews();
		setListeners();
		showUsers();
	}

	private void getViews() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_home = (Button) findViewById(R.id.btn_home);
		lv_follower = (ListView) findViewById(R.id.lv_following);
		adapter = new UserListAdapter(this, users);
		moreView = View.inflate(this, R.layout.footerview, null);
		lv_follower.addFooterView(moreView);
		lv_follower.setAdapter(adapter);
	}

	private void setListeners() {
		lv_follower.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub
				Log.v("listm", "begin");

				Intent intent = new Intent(FollowerListActivity.this,
						UserInfoActivity.class);

				intent.putExtra("user_id", users.get(position).getId());
				// intent.putExtra(name, value)
				FollowerListActivity.this.startActivity(intent);

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
		friendAPI.followers(userId, 20, next, false, new UserRequestListener());

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
			FollowerListActivity.this.h.sendMessage(message);
			// testView.setText(response);
		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			Util.showToast(FollowerListActivity.this, "获取数据异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Util.showToast(FollowerListActivity.this, "失败");

		}

	}

}
