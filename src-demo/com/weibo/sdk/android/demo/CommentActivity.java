package com.weibo.sdk.android.demo;

import java.io.IOException;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.*;
import com.weibo.sdk.android.api.WeiboAPI.COMMENTS_TYPE;
import com.weibo.sdk.android.demo.RepostActivity.RepostStatusRequestListener;
import com.weibo.sdk.android.requestlisteners.*;
import com.weibo.sdk.android.net.RequestListener;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CommentActivity extends Activity {

	private Button btn_send;
	private Button btn_clear;
	private EditText et_content;
	private Button btn_back;
	private CheckBox comment_repost;

	private long statusId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		statusId = CommentActivity.this.getIntent().getLongExtra("status_id",
				-1);
		getViews();
		addListeners();
	}

	private void getViews() {

		comment_repost = (CheckBox) findViewById(R.id.new_repost);
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
				CommentActivity.this.finish();
			}

		});

		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				String content = et_content.getText().toString();
				if (statusId != 0) {
					if (comment_repost.isChecked()) {

						new CommentsAPI(MainTabActivity.accessToken)
								.create(content, statusId, false,
										new CommentRequestListener(
												CommentActivity.this));
						new StatusesAPI(MainTabActivity.accessToken).repost(
								statusId, content, COMMENTS_TYPE.CUR_STATUSES,
								new NullRequestListener());

					}
					else {
						new CommentsAPI(MainTabActivity.accessToken)
								.create(content, statusId, false,
										new CommentRequestListener(
												CommentActivity.this));

					}
					CommentActivity.this.finish();
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
				CommentActivity.this.btn_clear.setText("" + (280 - length) / 2);

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
				CommentActivity.this.et_content.setText("");

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comment, menu);
		return true;
	}

}
