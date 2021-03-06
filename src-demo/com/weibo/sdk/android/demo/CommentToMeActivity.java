package com.weibo.sdk.android.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.adapter.CommentToMeAdapter;
import com.weibo.sdk.android.api.CommentsAPI;
import com.weibo.sdk.android.api.FavoritesAPI;
import com.weibo.sdk.android.api.WeiboAPI.AUTHOR_FILTER;
import com.weibo.sdk.android.api.WeiboAPI.SRC_FILTER;
import com.weibo.sdk.android.entity.Comment;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.requestlisteners.FavoriteRequestListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("HandlerLeak")
public class CommentToMeActivity extends Activity {

	private String TAG = "CommentAboutMe";
	private ListView listView;
	CommentToMeAdapter adapter;
	List<Comment> comments = new ArrayList<Comment>();
	private View moreView;
	private long sinceId = 0;
	private long maxId = 0;

	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			List<Comment> commentsList = Comment.getCommentsList((msg.getData()
					.getString("response")));

			if (commentsList.size() == 0) {
				Util.showToast(CommentToMeActivity.this, "无新的数据");
				return;
			}
			// Collections.sort(statusList);
			if (adapter.getCount() == 0) {
				adapter.comments = commentsList;
				comments = commentsList;
			}
			else {
				long firstId = adapter.comments.get(0).getId();
				for (Comment comment : commentsList) {
					if (comment == null)
						continue;

					if (comment.getId() > firstId) {
						Log.v("comment", comment.toString());
						adapter.comments.add(0, comment);
					}
					else
						if (comment.getId() < firstId) {
							Log.v("More", comment.getIdstr());
							if (comment.getId() < comments.get(
									comments.size() - 1).getId()) {
								// commentes.add(comment);
								adapter.comments.add(comment);
							}
						}

				}
			}
			adapter.notifyDataSetChanged();
			sinceId = adapter.comments.get(adapter.getCount() - 1).getId() - 1;
			maxId = ((Comment) adapter.getItem(0)).getId();

			Util.showToast(CommentToMeActivity.this, "更新完成");
			Log.v(TAG, msg.getData().getString("response"));
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylists);
		getViews();
		showComments(0);

	}

	public void getViews() {
		listView = (ListView) findViewById(R.id.mylist);

		adapter = new CommentToMeAdapter(this, comments);
		moreView = View.inflate(this, R.layout.footerview, null);
		listView.addFooterView(moreView);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO Auto-generated method stub

				final CharSequence[] choices = {"回复评论", "查看个人资料", "查看原微博"};
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						CommentToMeActivity.this.getParent());
				final int index = position;
				builder.setTitle("评论")

						// icon
						.setItems(
								choices,
								new android.content.DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										switch (which) {

											case 0 :
												Intent intent2comment = new Intent(
														CommentToMeActivity.this,
														CommentActivity.class);
												intent2comment.putExtra(
														"comment_id",
														adapter.getItemId(index));
												intent2comment
														.putExtra(
																"status_id",
																((Comment) adapter
																		.getItem(index))
																		.getStatus()
																		.getId());
												CommentToMeActivity.this
														.startActivity(intent2comment);
												break;
											case 1 :

												Intent intent = new Intent(
														CommentToMeActivity.this,
														UserInfoActivity.class);
												intent.putExtra(
														"user_id",
														((Comment) adapter
																.getItem(index))
																.getUser()
																.getId());

												startActivity(intent);
												break;

											case 2 :
												Intent intent2detail = new Intent(
														CommentToMeActivity.this,
														DetailActivity.class);
												intent2detail
														.putExtra(
																"status",
																((Comment) adapter
																		.getItem(index))
																		.getStatus());

												startActivity(intent2detail);

										}

									}
								});
				builder.create().show();

			}

		});
		moreView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				showComments(1);

			}

		});

	}

	private void showComments(int type) {
		// 0为添加并加载最新的微博，1为添加并加载之前的微博
		switch (type) {
			case 0 :
				new CommentsAPI(MainTabActivity.accessToken).toME(maxId, 0l,
						20, 1, AUTHOR_FILTER.ALL, SRC_FILTER.ALL,
						new CommentsRequestListener());
				break;

			case 1 :
				new CommentsAPI(MainTabActivity.accessToken).toME(0l, sinceId,
						20, 1, AUTHOR_FILTER.ALL, SRC_FILTER.ALL,
						new CommentsRequestListener());
				break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.at_me, menu);
		return true;
	}

	class CommentsRequestListener implements RequestListener {

		@Override
		public void onComplete(String response) {
			// TODO Auto-generated method stub

			Log.v(TAG, response);
			Message message = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("response", response);
			message.setData(bundle);
			Log.v(TAG, response);
			message.what = 1;
			CommentToMeActivity.this.h.sendMessage(message);
			// testView.setText(response);
		}

		@Override
		public void onIOException(IOException e) {
			// TODO Auto-generated method stub

			Util.showToast(CommentToMeActivity.this, "获取数据异常");
		}

		@Override
		public void onError(WeiboException e) {
			// TODO Auto-generated method stub
			Util.showToast(CommentToMeActivity.this, "失败");

		}

	}

}
