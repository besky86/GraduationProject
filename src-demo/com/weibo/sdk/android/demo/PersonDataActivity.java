package com.weibo.sdk.android.demo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PersonDataActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_data, menu);
		return true;
	}

}
