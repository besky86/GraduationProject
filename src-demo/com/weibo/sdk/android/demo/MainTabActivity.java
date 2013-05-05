package com.weibo.sdk.android.demo;

import com.weibo.sdk.android.Oauth2AccessToken;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

public class MainTabActivity extends TabActivity
		implements
			OnCheckedChangeListener {
	public static Oauth2AccessToken accessToken;
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

	@SuppressWarnings("deprecation")
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
		accessToken = (Oauth2AccessToken) intent
				.getSerializableExtra("AccessToken");
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

		iInfo = new Intent(this, MyInfoActivity.class);
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
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {

			case R.id.radio_home :
				tabhost.setCurrentTabByTag(HOME_TAB);
				// Delete by Lei@2013/04/28 DEL START
				// tabhost.setCurrentTab(checkedId);
				// Delete by Lei@2013/04/28 DEL END
				Toast.makeText(MainTabActivity.this, "button0",
						Toast.LENGTH_SHORT).show();

				break;
			case R.id.radio_news :
				tabhost.setCurrentTabByTag(NEWS_TAB);
				// Delete by Lei@2013/04/28 DEL START
				// tabhost.setCurrentTab(1);
				// Delete by Lei@2013/04/28 DEL END
				Toast.makeText(MainTabActivity.this, "button1",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.radio_info :
				tabhost.setCurrentTabByTag(INFO_TAB);
				// Delete by Lei@2013/04/28 DEL START
				// tabhost.setCurrentTab(2);
				// Delete by Lei@2013/04/28 DEL END
				Toast.makeText(MainTabActivity.this, "button2",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.radio_search :
				tabhost.setCurrentTabByTag(SEARCH_TAB);
				// Delete by Lei@2013/04/28 DEL START
				// tabhost.setCurrentTab(3);
				// Delete by Lei@2013/04/28 DEL END
				Toast.makeText(MainTabActivity.this, "button3",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.radio_more :
				tabhost.setCurrentTabByTag(MORE_TAB);
				// Delete by Lei@2013/04/28 DEL START
				// tabhost.setCurrentTab(4);
				// Delete by Lei@2013/04/28 DEL END
				Toast.makeText(MainTabActivity.this, "button4",
						Toast.LENGTH_SHORT).show();
				break;
		}
	}

}
