package com.weibo.sdk.android.demo;

import java.io.IOException;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.net.RequestListener;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class WriteWeiboActivity extends Activity {

	private Button btn_send;
	private ImageView inserted_iv;
	private Button btn_clear;
	private EditText et_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_write_weibo);
		getViews( );
		addListeners( );
	}

	private void getViews() {

		btn_send = (Button) findViewById(R.id.btn_send);
		inserted_iv = (ImageView) findViewById(R.id.add_image_iv);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		et_content = (EditText) findViewById(R.id.et_content);

	}

	private void addListeners() {
		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				String content = et_content.getText().toString();
				StatusesAPI statusAPI = new StatusesAPI(
						MainTabActivity.accessToken);
				statusAPI.update(content, "0.0", "0.0",
						new UpdateStatusRequestListener());

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write_weibo, menu);
		return true;
	}

	class UpdateStatusRequestListener implements RequestListener {

		@Override
		public void onComplete(String response) {
			// TODO Auto-generated method stub
		//	Toast.makeText(WriteWeiboActivity.this, response,
					//Toast.LENGTH_SHORT).show();

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

}
