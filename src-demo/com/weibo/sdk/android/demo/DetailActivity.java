package com.weibo.sdk.android.demo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;

import com.weibo.sdk.android.api.FavoritesAPI;
import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.requestlisteners.FavoriteRequestListener;
import com.weibo.sdk.android.util.AsynImageLoader;
import com.weibo.sdk.android.util.ImageCallback;
import com.weibo.sdk.android.util.StringUtil;
public class DetailActivity extends Activity {

	private Button btn_back;
	private TextView tv_username;
	private Button btn_home;
	private ImageView user_icon;
	private TextView txt_wb_item_content;
	private ImageView img_wb_item_content_pic;
	private TextView txt_wb_item_subcontent;
	private ImageView img_wb_item_content_subpic;
	private Button btn_repost;
	private Button btn_comment;
	private TextView tv_time;
	private TextView tv_source;
	private LinearLayout lyt_wb_item_sublayout;
	private Button to_repost;
	private Button to_comment;
	// private Button to_fav;

	private LinearLayout to_fav;
	private ImageView img_fav;
	private TextView fav_text;
	private Status status;
	private FrameLayout about_user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		status = (Status) getIntent().getSerializableExtra("status");
		getViews();
		setListeners();

	}

	private void getViews() {
		btn_back = (Button) findViewById(R.id.btn_back);
		tv_username = (TextView) findViewById(R.id.user_name);
		user_icon = (ImageView) findViewById(R.id.user_icon);
		txt_wb_item_content = (TextView) findViewById(R.id.txt_wb_item_content);
		img_wb_item_content_pic = (ImageView) findViewById(R.id.img_wb_item_content_pic);
		txt_wb_item_subcontent = (TextView) findViewById(R.id.txt_wb_item_subcontent);
		img_wb_item_content_subpic = (ImageView) findViewById(R.id.img_wb_item_content_subpic);

		btn_home = (Button) findViewById(R.id.btn_home);
		btn_repost = (Button) findViewById(R.id.btn_repost);
		btn_comment = (Button) findViewById(R.id.btn_comment);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_source = (TextView) findViewById(R.id.tv_source);

		to_repost = (Button) findViewById(R.id.to_repost);
		to_comment = (Button) findViewById(R.id.to_comment);
		to_fav = (LinearLayout) findViewById(R.id.to_fav);
		img_fav = (ImageView) findViewById(R.id.img_fav);
		fav_text = (TextView) findViewById(R.id.fav_text);
		about_user = (FrameLayout) findViewById(R.id.about_user);

		lyt_wb_item_sublayout = (LinearLayout) findViewById(R.id.lyt_wb_item_sublayout);

		if (status != null) {

			tv_username.setText(status.getUser().getScreen_name());
			Drawable image = AsynImageLoader.loadDrawable(status.getUser()
					.getProfile_image_url(), user_icon, new ImageCallback() {

				@Override
				public void imageSet(Drawable drawable, ImageView imageView) {
					imageView.setImageDrawable(drawable);
				}
			});

			if (image == null) {
				user_icon.setImageResource(R.drawable.detail_pic_loading);
			}
			else {
				user_icon.setImageDrawable(image);
			}

			txt_wb_item_content.setText(StringUtil.getSpannableString(status
					.getText()));

			tv_source.setText("来自:" + Html.fromHtml(status.getSource()));

			tv_time.setText(status.getCreated_at());

			// Delete by Lei@2013/05/05 DEL END

			// 判断微博中是否含有图片
			if (!StringUtil.isEmpty(status.getBmiddle_pic())) {

				img_wb_item_content_pic.setVisibility(View.VISIBLE);
				Drawable image_content_pic = AsynImageLoader.loadDrawable(
						status.getBmiddle_pic(), img_wb_item_content_pic,
						new ImageCallback() {

							@Override
							public void imageSet(Drawable drawable,
									ImageView imageView) {

								// TODO Auto-generated method stub
								imageView.setImageDrawable(drawable);
							}
						});

				if (image_content_pic == null) {
					img_wb_item_content_pic
							.setImageResource(R.drawable.detail_pic_loading);
				}
				else {
					img_wb_item_content_pic.setImageDrawable(image_content_pic);
				}
			}

			// 判断是否是转发
			if (status.getRetweeted_status() != null) {

				lyt_wb_item_sublayout.setVisibility(View.VISIBLE);

				txt_wb_item_subcontent = (TextView) findViewById(R.id.txt_wb_item_subcontent);
				if (status.getRetweeted_status().getText() != null)
					txt_wb_item_subcontent.setText(StringUtil
							.getSpannableString(status.getRetweeted_status()
									.getText()));

				// 判断微博中是否含有图片
				if (!StringUtil.isEmpty(status.getRetweeted_status()
						.getBmiddle_pic())) {

					img_wb_item_content_subpic.setVisibility(View.VISIBLE);

					Drawable image_subpic = AsynImageLoader.loadDrawable(status
							.getRetweeted_status().getBmiddle_pic(),
							img_wb_item_content_subpic, new ImageCallback() {

								@Override
								public void imageSet(Drawable drawable,
										ImageView imageView) {

									// TODO Auto-generated method stub
									imageView.setImageDrawable(drawable);
								}
							});

					if (image_subpic == null) {
						img_wb_item_content_subpic
								.setImageResource(R.drawable.detail_pic_loading);
					}
					else {
						img_wb_item_content_subpic
								.setImageDrawable(image_subpic);
					}
				}

			}

			// Log.v("com", "" + s.getComments_count());
			btn_comment.setText("" + status.getComments_count());

			btn_repost.setText("" + status.getReposts_count());

		}

	}

	private void setListeners() {

		about_user.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailActivity.this,
						UserInfoActivity.class);
				intent.putExtra("user_id", status.getUser().getId());
				startActivity(intent);

			}

		});
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				DetailActivity.this.finish();

			}

		});

		btn_repost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailActivity.this,
						RepostActivity.class);
				startActivity(intent);

			}

		});

		btn_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailActivity.this,
						CommentListActivity.class);
				intent.putExtra("status_id", status.getId());
				startActivity(intent);

			}

		});

		btn_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailActivity.this,
						MainTabActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
				startActivity(intent);

			}

		});

		to_repost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailActivity.this,
						RepostActivity.class);
				startActivity(intent);
			}

		});

		to_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent(DetailActivity.this,
						CommentActivity.class);
				startActivity(intent);
			}

		});

		to_fav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (fav_text.getText().toString().equals("收藏")) {
					new FavoritesAPI(MainTabActivity.accessToken).create(status
							.getId(), new FavoriteRequestListener(
							DetailActivity.this, fav_text.getText().toString()));
					fav_text.setText("取消收藏");

					img_fav.setBackgroundResource(R.drawable.attend);
				}
				else {
					new FavoritesAPI(MainTabActivity.accessToken).destroy(
							status.getId(), new FavoriteRequestListener(
									DetailActivity.this, fav_text.getText()
											.toString()));
					fav_text.setText("收藏");

					img_fav.setBackgroundResource(R.drawable.toolbar_fav_icon_nor);
				}

			}

		});

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

}
