package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.AccountAPI;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.entity.Status;
import com.weibo.sdk.android.net.RequestListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

/**
 * 测试读取JSon数据
 * 
 * @author lei
 * 
 */
public class TestActivity extends Activity {
	private final String TAG = "TestActivity";
	private TextView testView;

	// UI 更新只能在主线程中
	Handler h = new Handler() {

		public void handleMessage(Message msg) {
			// call update gui method.
			switch (msg.what) {

				case 1 :
					TestActivity.this.testView.setText(msg.getData().getString(
							"response"));

				case 2 :

					// JSON数据解析示例
					JSONArray jsonArray;
					List<Status> statusList = Status.getStatusesList((msg
							.getData().getString("response")));
					TestActivity.this.testView.setText(statusList.get(0)
							.getIdstr());

					// Delete by Lei@2013/04/23 DEL START
					// TestActivity.this.testView.setText(msg.getData().getString(
					// "response"));
					// Delete by Lei@2013/04/23 DEL END

					Log.v(TAG, msg.getData().getString("response"));
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		testView = (TextView) TestActivity.this.findViewById(R.id.test);
		Oauth2AccessToken accessToken = LoginActivity.accessToken;

		if (null != accessToken) {
			// AccountAPI account = new AccountAPI(accessToken);
			// account.getUid(new UIDRequestListener());
			StatusesAPI statuses = new StatusesAPI(accessToken);

			statuses.publicTimeline(20, 1, false, new StatusRequestListener());

		}
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

			// 标明不同的api函数所得结果
			message.what = 2;
			TestActivity.this.h.sendMessage(message);
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

	class UIDRequestListener implements RequestListener {

		@Override
		public void onComplete(String response) {
			// TODO Auto-generated method stub

			Log.v(TAG, response);
			Message message = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("response", response);
			message.setData(bundle);
			message.what = 1;
			TestActivity.this.h.sendMessage(message);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
}
