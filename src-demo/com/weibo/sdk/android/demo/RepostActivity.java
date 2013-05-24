package com.weibo.sdk.android.demo;

import java.io.IOException;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.WeiboAPI.COMMENTS_TYPE;
import com.weibo.sdk.android.net.RequestListener;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class RepostActivity extends Activity {

	private Button btn_send;
	private Button btn_clear;
	private EditText et_content;
	private Button btn_back;
	private CheckBox cb_add2comment;

	private long statusId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_repost);
		statusId = RepostActivity.this.getIntent()
				.getLongExtra("status_id", -1);
		getViews( );
		addListeners( );
		

	}

	private void getViews() {

		cb_add2comment = (CheckBox) findViewById(R.id.add2comment);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_back = (Button) findViewById(R.id.btn_back);

	}

	private void addListeners() {

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				RepostActivity.this.finish();
			}

		});

		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				String content = et_content.getText().toString();
				StatusesAPI statusAPI = new StatusesAPI(
						MainTabActivity.accessToken);
				if (statusId != 0) {

					if (cb_add2comment.isChecked()) {
						statusAPI.repost(statusId, content,
								COMMENTS_TYPE.CUR_STATUSES,
								new RepostStatusRequestListener());

					}
					else {
						statusAPI.repost(statusId, content, COMMENTS_TYPE.NONE,
								new RepostStatusRequestListener());

					}
					RepostActivity.this.finish();
				}
			}
		});

		// 给编辑框添加文本内容改变监听器
		et_content.addTextChangedListener(new TextWatcher() {

			private CharSequence temp;
			@Override
			public void afterTextChanged(Editable s) {

				// TODO Auto-generated method stub
				int length = 0;
				for (int index = 0; index < temp.length(); index++) {
					char tempChar = temp.charAt(index);

					// 判断中文字符
					if (// (tempChar > 0x00 && tempChar < 0xff)||
					(tempChar > '\u4E00' && tempChar < '\u9FA5')) {
						length += 2;
					}
					else
						length++;
				}
				RepostActivity.this.btn_clear.setText("" + (280 - length) / 2);

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

		btn_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				RepostActivity.this.et_content.setText("");

			}

		});

	}

	class RepostStatusRequestListener implements RequestListener {

		@Override
		public void onComplete(String response) {

			// TODO Auto-generated method stub
			Util.showToast(RepostActivity.this, "转发微博成功");
			RepostActivity.this.finish();

			Log.v("Update", response);

		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			Util.showToast(RepostActivity.this, "转发微博异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Util.showToast(RepostActivity.this, "转发微博失败");
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.repost, menu);
		return true;
	}

}
