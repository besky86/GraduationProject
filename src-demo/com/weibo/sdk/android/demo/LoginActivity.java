package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.util.Tools;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.UserInfoListAdapter;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.UsersAPI;
import com.weibo.sdk.android.entity.UserInfo;
import com.weibo.sdk.android.keep.AccessTokenKeeper;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.services.UserInfoService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class LoginActivity extends Activity {

	private UserInfoService service;
	private UserInfo user = new UserInfo();
	private ImageView image;

	private Spinner spinner;
	List<UserInfo> users = null;
	private ImageButton addAccountButton;
	private Button buttonLogin;
	private Weibo mWeibo;
	private static final String CONSUMER_KEY = "1325775247";// "966056985";//
															// 替换为开发者的appkey，例如"1646212860";
	private static final String REDIRECT_URL = "http://open.weibo.com/apps/1325775247/privilege/oauth";

	public static Oauth2AccessToken accessToken;

	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			// call update gui method.
			if (msg.obj instanceof UserInfo) {
				if (service.getUserInfoByUserId(user.getUserId()) == null) {

					service.insertUserInfo(user);

				}
				else {

					service.updateUserInfo(user);
				}
				refresh();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// 清除相应的应用Cookie
		// Add or Update by Lei@2013/04/22 UPD START
		// CookieSyncManager.createInstance(getApplicationContext());
		// CookieManager.getInstance().removeAllCookie();
		CookieSyncManager.createInstance(getApplicationContext());
		CookieManager.getInstance().removeAllCookie();
		// Add or Update by Lei@2013/04/22 UPD END

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getViews();
		refresh();

		setListeners();

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void refresh() {
		users = service.findAllUsers();
		user = service.getUserInfoByUserId(accessToken.getUid());

		if (users == null || users.isEmpty()) {
			return;
		}

		for (UserInfo userInfo : users) {
			if (userInfo.getUserId().equals(accessToken.getUid())) {
				user = userInfo;
				break;
			}
		}

		users.remove(user);
		users.add(0, user);

		Log.v("user", user.getUserName());
		final UserInfoListAdapter adapter = new UserInfoListAdapter(this, users);
		spinner.setAdapter(adapter);

	}

	private void getViews() {
		image = (ImageView) findViewById(R.id.head_icon);
		addAccountButton = (ImageButton) findViewById(R.id.add_user_button);
		service = new UserInfoService(this);

		accessToken = AccessTokenKeeper.readAccessToken(LoginActivity.this);

		mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
		spinner = (Spinner) findViewById(R.id.userInfo_spinner);
		buttonLogin = (Button) findViewById(R.id.login_button);

	}// end getViews

	private void setListeners() {

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				LoginActivity.this.user = (UserInfo) arg0
						.getItemAtPosition(arg2);
				Log.e("userId", "" + LoginActivity.this.user.getId());
				Log.e("userId", accessToken.getUid());
				Drawable icon = LoginActivity.this.user.getUserIcon();
				BitmapDrawable tempDrawable = (BitmapDrawable) icon;
				image.setImageBitmap(tempDrawable.getBitmap());
				accessToken.setUid(user.getUserId());
				accessToken.setToken(user.getToken());
				accessToken.setExpiresTime(user.getExpiresTime());
				AccessTokenKeeper.keepAccessToken(LoginActivity.this,
						accessToken);
				Log.e("access", accessToken.getUid());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		addAccountButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mWeibo.authorize(LoginActivity.this, new AuthDialogListener());
			}
		});

		buttonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (LoginActivity.accessToken.isSessionValid()) {
					if (LoginActivity.accessToken != null && user != null) {

						Log.e("access", accessToken.getUid());
						Intent intent = new Intent(LoginActivity.this,
								MainTabActivity.class);
						accessToken = AccessTokenKeeper
								.readAccessToken(LoginActivity.this);
						intent.putExtra("AccessToken",
								LoginActivity.accessToken);
						intent.putExtra("username", user.getUserName());
						intent.putExtra("user_id",
								Long.parseLong(user.getUserId()));

						Log.e("user", user.getUserId());

						LoginActivity.this.startActivity(intent);

					}
				}
				else {
					Toast.makeText(LoginActivity.this, "认证过期，请重新认证",
							Toast.LENGTH_SHORT).show();
				}

			}

		});
	}// end setListeners
	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			String uid = values.getString("uid");
			LoginActivity.accessToken = new Oauth2AccessToken(token,
					expires_in, uid);
			Log.v("token", token);
			// added by Lei@2013/04/12 UPD END

			// delete by Lei@2013/04/12 DEL START
			// MainActivity.accessToken = new Oauth2AccessToken(token,
			// expires_in);
			// delete by Lei@2013/04/12 DEL END
			if (LoginActivity.accessToken.isSessionValid()) {

				AccessTokenKeeper.keepAccessToken(LoginActivity.this,
						accessToken);
				if (null != accessToken) {
					new StatusesAPI(accessToken);
					UsersAPI usersAPI = new UsersAPI(accessToken);

					usersAPI.show(Long.parseLong(accessToken.getUid()),
							new UserInfoRequestListener());

				}

				// 授权之后清除所有与应用相关的Cookie
				// Add or Update by Lei@2013/04/22 UPD START
				CookieSyncManager.createInstance(getApplicationContext());
				CookieManager.getInstance().removeAllCookie();
				// Add or Update by Lei@2013/04/22 UPD END
				Toast.makeText(LoginActivity.this, "认证成功", Toast.LENGTH_SHORT)
						.show();

			}

		}

		@Override
		public void onError(WeiboDialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

	}

	class UserInfoRequestListener implements RequestListener {

		public static final String TAG = "UserInfoRequestListener";

		@Override
		public void onComplete(String response) {
			// TODO Auto-generated method stub
			Log.v(TAG, "onComplete" + response);
			try {
				// Add or Update by Lei@2013/04/24 UPD START

				JSONObject jsonObject = new JSONObject(response);
				user = new UserInfo();
				user.setUserName(jsonObject.getString("screen_name"));
				user.setUserId(accessToken.getUid());
				user.setToken(accessToken.getToken());
				user.setExpiresTime(accessToken.getExpiresTime());
				String url = jsonObject.getString("profile_image_url");
				// new Thread(new Runnable() {
				// @Override
				// public void run() {
				// // TODO Auto-generated method stub

				user.setUserIcon(Tools.getDrawablFromUrl(url));
				Message message = new Message();
				message.obj = user;
				LoginActivity.this.h.sendMessage(message);

				// }

				// }).start();

				// Add or Update by Lei@2013/04/24 UPD END
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

	}// end UserInfoRequestListener

}
