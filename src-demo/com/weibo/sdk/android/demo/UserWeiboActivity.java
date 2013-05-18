package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.*;
import com.weibo.sdk.android.api.*;
import com.weibo.sdk.android.api.WeiboAPI.FEATURE;

import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.requestlisteners.FavoriteRequestListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemLongClickListener;

public class UserWeiboActivity extends Activity {

	private static final String TAG = "FollowerAvtivity";
	private Button btn_back;
	private Button btn_home;
	private ListView lv_user_weibo;
	private View moreView;
	private long userId;
	long since = 0;

	long max = 0;
	FriendshipsAPI friendAPI = new FriendshipsAPI(MainTabActivity.accessToken);
	List<Status> statuses = new ArrayList<Status>();
	WeiboAdapter adapter;

	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			List<Status> statusList = Status.getStatusesList((msg.getData()
					.getString("response")));

			if (statusList.size() == 0) {
				Util.showToast(UserWeiboActivity.this, "无新的数据");
				return;
			}
			// Collections.sort(statusList);
			if (adapter.getCount() == 0) {
				adapter.status = statusList;

				statuses = statusList;

			}
			else {
				long firstId = adapter.status.get(0).getId();
				for (Status status : statusList) {
					if (status == null)
						continue;

					if (status.getId() > firstId) {
						Log.v("Status", status.toString());
						adapter.status.add(0, status);
					}
					else
						if (status.getId() < firstId) {
							Log.v("More", status.getIdstr());
							if (status.getId() < statuses.get(
									statuses.size() - 1).getId()) {
								// statuses.add(status);
								adapter.status.add(status);
							}
						}

				}
			}
			adapter.notifyDataSetChanged();
			max = adapter.status.get(adapter.getCount() - 1).getId() - 1;
			since = ((Status) adapter.getItem(0)).getId();

			Util.showToast(UserWeiboActivity.this, "更新完成");
			Log.v(TAG, msg.getData().getString("response"));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_weibo);
		userId = getIntent().getLongExtra("user_id", 0l);
		getViews();
		setListeners();
		showStatuses();
	}

	private void getViews() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_home = (Button) findViewById(R.id.btn_home);
		lv_user_weibo = (ListView) findViewById(R.id.lv_user_weibo);
		adapter = new WeiboAdapter(this, statuses);
		moreView = View.inflate(this, R.layout.footerview, null);
		lv_user_weibo.addFooterView(moreView);
		lv_user_weibo.setAdapter(adapter);
	}

	private void setListeners() {
		lv_user_weibo
				.setOnItemClickListener(new ListView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// TODO Auto-generated method stub
						Log.v("listm", "begin");

						Intent intent = new Intent(UserWeiboActivity.this,
								DetailActivity.class);

						intent.putExtra("status", statuses.get(position));
						// intent.putExtra(name, value)
						UserWeiboActivity.this.startActivity(intent);

					}

				});

		lv_user_weibo.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub
				final CharSequence[] choices = {"转发", "评论", "收藏"};
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						UserWeiboActivity.this);
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
														UserWeiboActivity.this,
														RepostActivity.class);
												intent.putExtra("status_id",
														statuses.get(index)
																.getId());
												UserWeiboActivity.this
														.startActivity(intent);
												break;

											case 1 :
												Intent intent2comment = new Intent(
														UserWeiboActivity.this,
														CommentActivity.class);
												intent2comment.putExtra(
														"status_id", statuses
																.get(index)
																.getId());
												UserWeiboActivity.this
														.startActivity(intent2comment);
												break;
											case 2 :

												new FavoritesAPI(
														MainTabActivity.accessToken)
														.create(statuses.get(
																index).getId(),
																new FavoriteRequestListener(
																		UserWeiboActivity.this,
																		"收藏"));
												break;

										}

									}
								});
				builder.create().show();
				return false;

			}

		});

		moreView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				showStatuses();

			}

		});

	}
	private void showStatuses() {
		new StatusesAPI(MainTabActivity.accessToken).userTimeline(userId, 0,
				max, 20, 1, false, FEATURE.ALL, false,
				new StatusRequestListener());
		// friendAPI.followers(userId, 20, next, false, );

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.following, menu);
		return true;
	}

	class StatusRequestListener implements RequestListener {

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
			UserWeiboActivity.this.h.sendMessage(message);
			// testView.setText(response);
		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			Util.showToast(UserWeiboActivity.this, "获取数据异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Util.showToast(UserWeiboActivity.this, "失败");

		}

	}

}
