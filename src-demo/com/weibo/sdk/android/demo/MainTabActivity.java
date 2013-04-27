package com.weibo.sdk.android.demo;

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
	private RadioGroup mainTab;
	private TabHost tabhost;
	private Intent iHome;
	private Intent iNews;
	private Intent iInfo;
	private Intent iSearch;
	private Intent iMore;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		mainTab.setOnCheckedChangeListener(this);
		tabhost = getTabHost();
		// 在此进行Intent的初始化,设置Intent将传进那个Activity
		iHome = new Intent(this, HomeActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("iHome")
				.setIndicator(getResources().getString(R.string.main_home),
						getResources().getDrawable(R.drawable.icon_1_n))
				.setContent(iHome));

		iNews = new Intent(this, NewsActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("iNews")
				.setIndicator(getResources().getString(R.string.main_news),
						getResources().getDrawable(R.drawable.icon_2_n))
				.setContent(iNews));

		iInfo = new Intent(this, MyInfoActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("iInfo")
				.setIndicator(getResources().getString(R.string.main_my_info),
						getResources().getDrawable(R.drawable.icon_3_n))
				.setContent(iInfo));

		iSearch = new Intent(this, SearchActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("iSearch")
				.setIndicator(getResources().getString(R.string.menu_search),
						getResources().getDrawable(R.drawable.icon_4_n))
				.setContent(iSearch));

		iMore = new Intent(this, MoreActivity.class);
		tabhost.addTab(tabhost
				.newTabSpec("iMore")
				.setIndicator(getResources().getString(R.string.more),
						getResources().getDrawable(R.drawable.icon_5_n))
				.setContent(iMore));
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {

			case R.id.radio_button0 :
				tabhost.setCurrentTab(0);
				Toast.makeText(MainTabActivity.this, "button0",
						Toast.LENGTH_SHORT).show();

				break;
			case R.id.radio_button1 :
				tabhost.setCurrentTab(1);
				Toast.makeText(MainTabActivity.this, "button1",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.radio_button2 :
				tabhost.setCurrentTab(2);
				Toast.makeText(MainTabActivity.this, "button2",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.radio_button3 :
				tabhost.setCurrentTab(3);
				Toast.makeText(MainTabActivity.this, "button3",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.radio_button4 :
				tabhost.setCurrentTab(4);
				Toast.makeText(MainTabActivity.this, "button4",
						Toast.LENGTH_SHORT).show();
				break;
		}
	}

}
