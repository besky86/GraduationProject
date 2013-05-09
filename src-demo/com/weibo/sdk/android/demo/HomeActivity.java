package com.weibo.sdk.android.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.demo.TestActivity.StatusRequestListener;
import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.requestlisteners.FavoriteRequestListener;

import com.weibo.sdk.android.adapter.*;
import com.weibo.sdk.android.api.FavoritesAPI;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.api.WeiboAPI;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class HomeActivity extends Activity {

	private static final String TAG = "HomeActivity";

	// 加载View
	// private View progresView;

	UsersAPI userAPI = new UsersAPI(MainTabActivity.accessToken);
	StatusesAPI statusesAPI = new StatusesAPI(MainTabActivity.accessToken);

	private View titleView;
	private Button btn_write;
	private Button btn_refresh;

	private ListView weiboListView;

	Handler h = new Handler() {

		public void handleMessage(Message msg) {
			List<Status> statusList = Status.getStatusesList((msg.getData()
					.getString("response")));
			for (Status status : statusList) {
				Log.v("User", status.getUid());
				if (status.getUser() == null) {
					userAPI.show(Long.parseLong(status.getUid()),
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
		// weiboListView = (ListView) findViewById(R.id.lv_weibos);
		getViews();
		addListeners();
		showStatuses();

	}

	private void getViews() {
		weiboListView = (ListView) findViewById(R.id.lv_weibos);
		btn_write = (Button) findViewById(R.id.btn_writer);
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		TextView tv = (TextView) findViewById(R.id.tv_username);
		
		tv.getPaint().setFakeBoldText(true); //设置仿粗体
		tv.setText(MainTabActivity.userName);

	}

	/**
	 * 
	 * addListeners 给组件绑定监听器 void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void addListeners() {
		// 转到写微博界面
		btn_write.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this,
						WriteWeiboActivity.class);
				HomeActivity.this.startActivity(intent);

			}

		});

		// 更新微博
		btn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				showStatuses();

			}

		});

	}
	/**
	 * showStatuses 显示最新微博 void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void showStatuses() {
		if (null != MainTabActivity.accessToken) {

			statusesAPI.homeTimeline(0l, 0l, 20, 1, false,
					WeiboAPI.FEATURE.ALL, true, new StatusesRequestListener());
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

				userAPI.show(status.getUid(), new UserRequestListener(status));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void refresh(final List<Status> statuses) {

		WeiboAdapter adapter = new WeiboAdapter(this, statuses);
		weiboListView.setAdapter(adapter);
		weiboListView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

				// TODO Auto-generated method stub

			}

		});

		weiboListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub
				final CharSequence[] choices = {"转发", "评论", "收藏", "查看个人资料"};
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						HomeActivity.this);
				final int index = position;
				builder.setTitle("微博功能")

						// icon
						.setItems(
								choices,
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										switch (which) {
											case 0 :

												Intent intent = new Intent(
														HomeActivity.this,
														RepostActivity.class);
												intent.putExtra("status_id",
														statuses.get(index)
																.getId());
												HomeActivity.this
														.startActivity(intent);
												break;

											case 1 :
												Intent intent2comment = new Intent(
														HomeActivity.this,
														CommentActivity.class);
												intent2comment.putExtra(
														"status_id", statuses
																.get(index)
																.getId());
												HomeActivity.this
														.startActivity(intent2comment);
												break;
											case 2 :

												new FavoritesAPI(
														MainTabActivity.accessToken)
														.create(statuses.get(
																index).getId(),
																new FavoriteRequestListener(
																		HomeActivity.this));
												break;

										}

									}
								});
				builder.create().show();
				return false;

			}

		});

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

			Util.showToast(HomeActivity.this, "获取数据异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Util.showToast(HomeActivity.this, "失败");

		}

	}

}
