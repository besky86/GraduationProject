package com.weibo.sdk.android.demo;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.keep.AccessTokenKeeper;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity
		implements
			OnCheckedChangeListener {
	public static Oauth2AccessToken accessToken;
	public static String userName;
	public static long userId;
	private RadioGroup mainTab;
	private TabHost tabhost;
	private Intent iHome;
	private Intent iNews;
	private Intent iInfo;
	private Intent iSearch;
	private Intent iMore;
	private final String HOME_TAB = "Home";
	private final String NEWS_TAB = "News";
	private final String INFO_TAB = "MyInfo";
	private final String SEARCH_TAB = "Search";
	private final String MORE_TAB = "More";
	public static Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// Add or Update by Lei@2013/05/05 UPD START
		// Intent intent = MainTabActivity.this.getIntent();
		// accessToken = (Oauth2AccessToken) intent
		// .getSerializableExtra("AccessToken");
		Intent intent = MainTabActivity.this.getIntent();
		accessToken = AccessTokenKeeper.readAccessToken(MainTabActivity.this);
		userName = intent.getStringExtra("username");
		
		Log.e("user", userName);
		userId = intent.getLongExtra("user_id", -1);
		Log.e("user", "" + userId);
		// Add or Update by Lei@2013/05/05 UPD END
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		mainTab.setOnCheckedChangeListener(this);
		tabhost = getTabHost();
		// 在此进行Intent的初始化,设置Intent将传进那个Activity
		iHome = new Intent(this, HomeActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec(HOME_TAB)
				.setIndicator(getResources().getString(R.string.main_home),
						getResources().getDrawable(R.drawable.icon_1_n))
				.setContent(iHome));

		iNews = new Intent(this, NewsActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec(NEWS_TAB)
				.setIndicator(getResources().getString(R.string.main_news),
						getResources().getDrawable(R.drawable.icon_2_n))
				.setContent(iNews));

		iInfo = new Intent(this, UserInfoActivity.class);

		iInfo.putExtra("user_id",
				Long.parseLong(MainTabActivity.accessToken.getUid()));
		Log.e("Error",
				"" + Long.parseLong(MainTabActivity.accessToken.getUid()));
		tabhost.addTab(tabhost
				.newTabSpec(INFO_TAB)
				.setIndicator(getResources().getString(R.string.main_my_info),
						getResources().getDrawable(R.drawable.icon_3_n))
				.setContent(iInfo));

		iSearch = new Intent(this, SearchActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec(SEARCH_TAB)
				.setIndicator(getResources().getString(R.string.menu_search),
						getResources().getDrawable(R.drawable.icon_4_n))
				.setContent(iSearch));

		iMore = new Intent(this, MoreActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec(MORE_TAB)
				.setIndicator(getResources().getString(R.string.more),
						getResources().getDrawable(R.drawable.icon_5_n))
				.setContent(iMore));

		activity = this;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {

			case R.id.radio_home :
				tabhost.setCurrentTabByTag(HOME_TAB);
				break;
			case R.id.radio_news :
				tabhost.setCurrentTabByTag(NEWS_TAB);
				break;
			case R.id.radio_info :
				tabhost.setCurrentTabByTag(INFO_TAB);
				break;
			case R.id.radio_search :
				tabhost.setCurrentTabByTag(SEARCH_TAB);
				break;
			case R.id.radio_more :
				tabhost.setCurrentTabByTag(MORE_TAB);
				break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.ActivityGroup#onPause()
	 */
	@Override
	protected void onPause() {

		// TODO Auto-generated method stub
		super.onPause();

	}

}
