package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.SearchUsertAdapter;
import com.weibo.sdk.android.api.SearchAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.demo.HomeActivity.UserRequestListener;
import com.weibo.sdk.android.entity.Status;
import com.weibo.sdk.android.entity.User;
import com.weibo.sdk.android.entity.User2Search;
import com.weibo.sdk.android.net.RequestListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity {
	private final String TAG = "SearchActivity";

	private EditText et_search;
	private Button btn_clear;
	private Button btn_search;
	private ListView user_lv;
	private String search_content;

	SearchUsertAdapter adapter = new SearchUsertAdapter();
	List<User2Search> users = new ArrayList<User2Search>();
	List<User2Search> searchResult;

	Handler h = new Handler() {
		public void handleMessage(Message msg) {

			List<User2Search> resultList = User2Search.getResultByString(msg
					.getData().getString("response"));
			if (resultList.size() == 0) {
				Util.showToast(SearchActivity.this, "无新的数据");
				return;
			}
			// Collections.sort(statusList);

			for (User2Search user : resultList) {
				adapter.users.add(user);
			}
			adapter.users = resultList;

			adapter.notifyDataSetChanged();

			Util.showToast(SearchActivity.this, "更新完成");
			Log.v(TAG, msg.getData().getString("response"));
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		// adapter.users = users;
		getViews();
		setListeners();

	}

	private void getViews() {

		et_search = (EditText) findViewById(R.id.et_search);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_search = (Button) findViewById(R.id.btn_search);
		// adapter = new SearchUsertAdapter(this, users);
		user_lv = (ListView) findViewById(R.id.user_search_list);

		adapter = new SearchUsertAdapter(this, users);
		user_lv.setAdapter(adapter);

	}

	private void setListeners() {

		et_search.addTextChangedListener(new TextWatcher() {

			private CharSequence temp;
			@Override
			public void afterTextChanged(Editable s) {

				// TODO Auto-generated method stub
				if (temp.length() != 0) {
					btn_clear.setVisibility(View.VISIBLE);
				}
				else
					btn_clear.setVisibility(View.GONE);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				// TODO Auto-generated method stub
				temp = s;

			}

		});
		user_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchActivity.this,
						UserInfoActivity.class);

				intent.putExtra("user_id", adapter.getItemId(arg2));
				// intent.putExtra(name, value)
				SearchActivity.this.startActivity(intent);

			}

		});

		btn_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				et_search.setText("");

			}

		});

		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				search_content = et_search.getText().toString();
				// new SearchAPI(MainTabActivity.accessToken).users(
				// URLEncoder.encode(search_content), 10,
				// new SearchRequestListener());
				new SearchAPI(MainTabActivity.accessToken).users(
						search_content, 50, new SearchRequestListener());

				Log.v(TAG, URLEncoder.encode(search_content));

			}

		});

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	class SearchRequestListener implements RequestListener {

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
			SearchActivity.this.h.sendMessage(message);
			// testView.setText(response);
		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			Util.showToast(SearchActivity.this, "获取数据异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Util.showToast(SearchActivity.this, "失败");

		}
	}

}
