package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.WeiboAdapter;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.WeiboAPI.AUTHOR_FILTER;
import com.weibo.sdk.android.api.WeiboAPI.SRC_FILTER;
import com.weibo.sdk.android.api.WeiboAPI.TYPE_FILTER;
import com.weibo.sdk.android.entity.Status;
import com.weibo.sdk.android.net.RequestListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

@SuppressLint("HandlerLeak")
public class AtMeActivity extends Activity {

	private String TAG = "ATME";
	private ListView listView;
	WeiboAdapter adapter;
	List<Status> statuses = new ArrayList<Status>();
	private View moreView;
	private long sinceId = 0;
	private long maxId = 0;

	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			List<Status> statusList = Status.getStatusesList((msg.getData()
					.getString("response")));

			if (statusList.size() == 0) {
				Util.showToast(AtMeActivity.this, "无新的数据");
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
			sinceId = adapter.status.get(adapter.getCount() - 1).getId() - 1;
			maxId = ((Status) adapter.getItem(0)).getId();

			Util.showToast(AtMeActivity.this, "更新完成");
			Log.v(TAG, msg.getData().getString("response"));
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylists);
		getViews();
		showWeibos(0);

	}

	public void getViews() {
		listView = (ListView) findViewById(R.id.mylist);

		adapter = new WeiboAdapter(this, statuses);
		moreView = View.inflate(this, R.layout.footerview, null);
		listView.addFooterView(moreView);
		listView.setAdapter(adapter);

		moreView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				showWeibos(1);

			}

		});

	}

	private void showWeibos(int type) {
		// 0为添加并加载最新的微博，1为添加并加载之前的微博
		switch (type) {
			case 0 :
				new StatusesAPI(MainTabActivity.accessToken).mentions(maxId,
						0l, 20, 1, AUTHOR_FILTER.ALL, SRC_FILTER.ALL,
						TYPE_FILTER.ALL, false, new StatusRequestListener());
				break;

			case 1 :
				new StatusesAPI(MainTabActivity.accessToken).mentions(0l,
						sinceId, 20, 1, AUTHOR_FILTER.ALL, SRC_FILTER.ALL,
						TYPE_FILTER.ALL, false, new StatusRequestListener());
				break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.at_me, menu);
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
			AtMeActivity.this.h.sendMessage(message);
			// testView.setText(response);
		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			Util.showToast(AtMeActivity.this, "获取数据异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Util.showToast(AtMeActivity.this, "失败");

		}

	}

}
