package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.demo.TestActivity.StatusRequestListener;
import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.net.RequestListener;

import com.weibo.sdk.android.adapter.*;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.api.WeiboAPI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class HomeActivity extends Activity {

	private static final String TAG = "HomeActivity";

	// 加载View
	// private View progresView;

	private View titleView;

	private ListView weiboListView;

	Handler h = new Handler() {

		public void handleMessage(Message msg) {
			List<Status> statusList = Status.getStatusesList((msg.getData()
					.getString("response")));
			for (Status status : statusList) {
				Log.v("User", status.getUid());
				if (status.getUser() == null) {
					UsersAPI users = new UsersAPI(MainTabActivity.accessToken);
					users.show(Long.parseLong(status.getUid()),
							new UserRequestListener(status));
				}
			}
			// getUsers(statusList);
			refresh(statusList);
			Log.v(TAG, msg.getData().getString("response"));

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		weiboListView = (ListView) findViewById(R.id.lv_weibos);
		
		showStatuses();

	}

	/**
	 * showStatuses 显示最新微博
	 *void
	 * @exception 
	 * @since  1.0.0
	*/
	private void showStatuses() {
		if (null != MainTabActivity.accessToken) {
			// AccountAPI account = new AccountAPI(accessToken);
			// account.getUid(new UIDRequestListener());
			StatusesAPI statuses = new StatusesAPI(MainTabActivity.accessToken);
			statuses.homeTimeline(0l, 0l, 20, 1, false, WeiboAPI.FEATURE.ALL,
					true, new StatusesRequestListener());
		}
	}

	/**
	 * getUsers(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	protected void getUsers(List<Status> statuses) {

		// TODO Auto-generated method stub
		for (Status status : statuses) {
			if (status.getUser() == null) {
				UsersAPI users = new UsersAPI(MainTabActivity.accessToken);
				users.show(status.getUid(), new UserRequestListener(status));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void refresh(List<Status> statuses) {

		WeiboAdapter adapter = new WeiboAdapter(this, statuses);
		weiboListView.setAdapter(adapter);

		Log.i(TAG, "ok--------");
	}

	class UserRequestListener implements RequestListener {

		Status status;
		public UserRequestListener(Status s) {
			this.status = s;
		}
		@Override
		public void onComplete(String response) {

			// TODO Auto-generated method stub
			JSONObject userJson;
			try {
				userJson = new JSONObject(response);

				Log.v("User", response);
				status.setUser(User.getUserByJSON(userJson));
			}
			catch (JSONException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			// Delete by Lei@2013/05/06 DEL START
			// Message message = new Message();
			// Bundle bundle = new Bundle();
			// bundle.putString("response", response);
			// bundle.putSerializable("status", status);
			// message.setData(bundle);
			// Log.v(TAG, response);
			// message.what = 2;
			// HomeActivity.this.h.sendMessage(message);
			// Delete by Lei@2013/05/06 DEL END
			// testView.setText(response);

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
	class StatusesRequestListener implements RequestListener {

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
			HomeActivity.this.h.sendMessage(message);
			// testView.setText(response);
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

}
