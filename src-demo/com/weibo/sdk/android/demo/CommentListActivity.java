package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.*;
import com.weibo.sdk.android.api.*;
import com.weibo.sdk.android.api.WeiboAPI.AUTHOR_FILTER;

import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.net.RequestListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

@SuppressLint("HandlerLeak")
public class CommentListActivity extends Activity {

	UsersAPI userAPI = new UsersAPI(MainTabActivity.accessToken);
	CommentsAPI commentsAPI = new CommentsAPI(MainTabActivity.accessToken);
	// List<Comment> commentes = new ArrayList<Comment>();
	List<Comment> comments = new ArrayList<Comment>();
	CommentListAdapter adapter;
	// private int visibleLastIndex = 0; // 最后的可视项索引
	// private int visibleItemCount; // 当前窗口可见项总数

	private long id;
	// private View titleView;
	private Button btn_back;
	private Button btn_comment;

	private View moreView;
	private ListView commentListView;

	Handler h = new Handler() {
		public void handleMessage(Message msg) {

			List<Comment> commentList = Comment.getCommentsList(msg.getData()
					.getString("response"));

			if (commentList.size() == 0) {
				Util.showToast(CommentListActivity.this, "更新完成");
				return;
			}
			// Collections.sort(commentList);
			if (adapter.getCount() == 0) {
				adapter.comments = commentList;

				comments = commentList;

				for (Comment comment : commentList) {
					// Log.v("User", comment.getUid());
					if (comment == null)
						continue;
					comment.setCreated_at(comment.getCreated_at().replace(
							"+0800 ", ""));

				}
			}
			else {

				for (Comment comment : commentList) {
					if (comment == null)
						continue;
					if (comment.getId() < comments.get(comments.size() - 1)
							.getId()) {
						// statuses.add(status);
						comment.setCreated_at(comment.getCreated_at().replace(
								"+0800 ", ""));
						adapter.comments.add(comment);
					}
				}

			}
			adapter.notifyDataSetChanged();
			// Util.showToast(HomeActivity.this, "更新完成");
			// Log.v(TAG, msg.getData().getString("response"));

		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_list);

		getViews();
		addListeners();
		showComments();
	}
	/**
	 * showComments(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	private void showComments() {

		// TODO Auto-generated method stub
		if (null != MainTabActivity.accessToken) {

			if (adapter.getCount() == 0)
				commentsAPI.show(id, 0l, 0l, 20, 1, AUTHOR_FILTER.ALL,
						new CommentsRequestListener());
			else
				commentsAPI
						.show(id, 0l,
								adapter.comments.get(adapter.getCount() - 1)
										.getId(), 20, 1, AUTHOR_FILTER.ALL,
								new CommentsRequestListener());

		}

	}

	private void getViews() {
		commentListView = (ListView) findViewById(R.id.lv_comments);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_comment = (Button) findViewById(R.id.btn_comment);

		adapter = new CommentListAdapter(this, comments);
		moreView = View.inflate(this, R.layout.footerview, null);
		commentListView.addFooterView(moreView);
		commentListView.setAdapter(adapter);
		id = getIntent().getLongExtra("status_id", 0l);
	}

	private void addListeners() {

		moreView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Log.v("More", "begin");

				showComments();

			}

		});

		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				CommentListActivity.this.finish();

			}

		});

		btn_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(CommentListActivity.this,
						CommentActivity.class);
				intent.putExtra("status_id", id);
				startActivity(intent);

			}

		});

	}

	class CommentsRequestListener implements RequestListener {

		@Override
		public void onComplete(String response) {
			// TODO Auto-generated method stub
			// Log.v(TAG, response);
			Message message = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("response", response);
			message.setData(bundle);
			// Log.v(TAG, response);
			message.what = 1;
			CommentListActivity.this.h.sendMessage(message);
			Log.v("comments", response);

		}
		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			Util.showToast(CommentListActivity.this, "获取数据异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Log.e("Weibo error", e.getMessage().toString());
			Util.showToast(CommentListActivity.this, "失败");

		}

	}

}
