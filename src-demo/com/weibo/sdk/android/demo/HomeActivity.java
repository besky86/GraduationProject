package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.requestlisteners.FavoriteRequestListener;

import com.weibo.sdk.android.adapter.*;
import com.weibo.sdk.android.api.FavoritesAPI;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.api.WeiboAPI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class HomeActivity extends Activity implements OnScrollListener {

	private static final String TAG = "HomeActivity";

	// 加载View
	// private View progresView;

	UsersAPI userAPI = new UsersAPI(MainTabActivity.accessToken);
	StatusesAPI statusesAPI = new StatusesAPI(MainTabActivity.accessToken);
	List<Status> statuses = new ArrayList<Status>();
	WeiboAdapter adapter;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private Button btn_write;
	private Button btn_refresh;

	private View moreView;
	private ListView weiboListView;

	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			List<Status> statusList = Status.getStatusesList((msg.getData()
					.getString("response")));

			if (statusList.size() == 0) {
				Util.showToast(HomeActivity.this, "无新的数据");
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
					if (status.getUser() == null) {
						userAPI.show(Long.parseLong(status.getUid()),
								new UserRequestListener(status));
					}
				}
			}
			adapter.notifyDataSetChanged();
			Util.showToast(HomeActivity.this, "更新完成");
			Log.v(TAG, msg.getData().getString("response"));

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// weiboListView = (ListView) findViewById(R.id.lv_weibos);
		// moreView = View.inflate(this, R.layout.footerview, null);

		// weiboListView.addFooterView(moreView);
		getViews();
		addListeners();
		showStatuses();

	}

	@Override
	protected void onPause() {

		// TODO Auto-generated method stub
		super.onPause();

	}

	private void getViews() {
		weiboListView = (ListView) findViewById(R.id.lv_weibos);
		btn_write = (Button) findViewById(R.id.btn_writer);
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		TextView tv = (TextView) findViewById(R.id.tv_username);

		tv.getPaint().setFakeBoldText(true); // 设置仿粗体
		tv.setText(MainTabActivity.userName);
		adapter = new WeiboAdapter(this, statuses);
		moreView = View.inflate(this, R.layout.footerview, null);
		weiboListView.addFooterView(moreView);
		weiboListView.setAdapter(adapter);

		// weiboListView.setFooterDividersEnabled(true);

	}

	/**
	 * 
	 * addListeners 给组件绑定监听器 void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void addListeners() {

		moreView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Log.v("More", "begin");

				if (adapter.getCount() == 0)
					return;
				statusesAPI.homeTimeline(0l,
						adapter.status.get(adapter.getCount() - 1).getId() - 1,
						20, 1, false, WeiboAPI.FEATURE.ALL, false,
						new StatusesRequestListener());

			}

		});

		weiboListView.setOnScrollListener(this);
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

		// Delete by Lei@2013/05/20 DEL START
		// // 更新微博
		btn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				showStatuses();

			}

		});

		weiboListView
				.setOnItemClickListener(new ListView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						// TODO Auto-generated method stub
						Log.v("listm", "begin");

						Intent intent = new Intent(HomeActivity.this,
								DetailActivity.class);

						intent.putExtra("status", statuses.get(position));
						// intent.putExtra(name, value)
						HomeActivity.this.startActivity(intent);

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
																		HomeActivity.this,
																		"收藏"));
												break;

										}

									}
								});
				builder.create().show();
				return false;

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

			if (statuses.size() > 0) {
				statusesAPI.homeTimeline(statuses.get(0).getId(), 0l, 20, 1,
						false, WeiboAPI.FEATURE.ALL, false,
						new StatusesRequestListener());
			}
			else

				statusesAPI.homeTimeline(0l, 0l, 20, 1, false,
						WeiboAPI.FEATURE.ALL, false,
						new StatusesRequestListener());
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

	public void refresh() {

		adapter = new WeiboAdapter(this, statuses);
		weiboListView.setAdapter(adapter);

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
																		HomeActivity.this,
																		"收藏"));
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

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		// TODO Auto-generated method stub
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {
			// 如果是自动加载,可以在这里放置异步加载数据的代码
			Log.i("LOADMORE", "loading...");

		}

	}

}
