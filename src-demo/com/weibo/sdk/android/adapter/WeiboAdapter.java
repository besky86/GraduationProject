package com.weibo.sdk.android.adapter;

import java.util.List;

import com.weibo.sdk.android.demo.R;

import com.weibo.sdk.android.entity.*;
import com.weibo.sdk.android.util.AsynImageLoader;
import com.weibo.sdk.android.util.ImageCallback;
import com.weibo.sdk.android.util.StringUtil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * WeiboAdapter 2013-5-5 上午9:29:18
 * 
 * @version 1.0.0
 * 
 */
public class WeiboAdapter extends BaseAdapter {

	public List<Status> status;

	private LayoutInflater mInflater;

	public WeiboAdapter(Context context, List<Status> status) {

		this.status = status;
		mInflater = LayoutInflater.from(context);

	}

	public int getCount() {
		return status == null ? 0 : status.size();
	}

	public Object getItem(int position) {
		return status == null ? null : status.get(position);
	}

	public long getItemId(int position) {
		return status.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return createViewFromResource(position, convertView);
	}

	private View createViewFromResource(int position, View convertView) {
		View v;
		v = mInflater.inflate(R.layout.wb_item_template, null);

		bindView(position, v);

		return v;
	}

	/**
	 * bindView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param position
	 * @param v
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void bindView(int position, View view) {

		// TODO Auto-generated method stub

		WeiboHolder holder = new WeiboHolder();

		Status s = this.status.get(position);
		holder.txt_wb_item_uname = (TextView) view
				.findViewById(R.id.txt_wb_item_uname);

		holder.txt_wb_item_content = (TextView) view
				.findViewById(R.id.txt_wb_item_content);
		holder.txt_wb_item_from = (TextView) view
				.findViewById(R.id.txt_wb_item_from);
		holder.img_wb_item_head = (ImageView) view
				.findViewById(R.id.img_wb_item_head);

		holder.img_wb_item_head = (ImageView) view
				.findViewById(R.id.img_wb_item_head);

		// holder.img_wb_item_head = (ImageView) view
		// .findViewById(R.id.img_wb_item_head);
		// holder.txt_wb_item_from.setMovementMethod(LinkMovementMethod
		// .getInstance());

		Log.v("Image", holder.img_wb_item_head.toString());

		holder.txt_wb_item_uname.getPaint().setFakeBoldText(true); // 设置仿粗体
		if (s.getUser() == null) {
			holder.txt_wb_item_uname.setText(s.getUid());
		}
		else {
			Log.v("Userid", s.getUser().getProfile_image_url());
			holder.txt_wb_item_uname.setText(s.getUser().getScreen_name());
			Log.v("Userid", s.getUser().getIdstr());
			Drawable image = AsynImageLoader.loadDrawable(s.getUser()
					.getProfile_image_url(), holder.img_wb_item_head,
					new ImageCallback() {

						@Override
						public void imageSet(Drawable drawable,
								ImageView imageView) {

							// TODO Auto-generated method stub
							imageView.setImageDrawable(drawable);
						}
					});

			if (image == null) {
				holder.img_wb_item_head.setImageResource(R.drawable.usericon);
			}
			else {
				holder.img_wb_item_head.setImageDrawable(image);
			}

			// 判断是否通过认证
			if (s.getUser().isVerified()) {
				holder.img_wb_item_V = (ImageView) view
						.findViewById(R.id.img_wb_item_V);
				holder.img_wb_item_V.setVisibility(View.VISIBLE);
			}

			// SimpleImageLoader.showImg(s.getUser().getProfile_image_url(),
			// holder.img_wb_item_head);

		}

		holder.txt_wb_item_content.setText(StringUtil.getSpannableString(s
				.getText()));
		Log.v("ListView", s.getText());
		Log.v("ID", s.getIdstr());

		holder.txt_wb_item_from.setText("来自:" + Html.fromHtml(s.getSource()));

		holder.txt_wb_item_time = (TextView) view
				.findViewById(R.id.txt_wb_item_time);
		holder.txt_wb_item_time.setText(s.getCreated_at());

		// Delete by Lei@2013/05/05 DEL END

		// 判断微博中是否含有图片
		if (!StringUtil.isEmpty(s.getThumbnail_pic())) {
			holder.img_wb_item_content_pic = (ImageView) view
					.findViewById(R.id.img_wb_item_content_pic);
			holder.img_wb_item_content_pic.setVisibility(View.VISIBLE);
			Drawable image = AsynImageLoader.loadDrawable(s.getThumbnail_pic(),
					holder.img_wb_item_content_pic, new ImageCallback() {

						@Override
						public void imageSet(Drawable drawable,
								ImageView imageView) {

							// TODO Auto-generated method stub
							imageView.setImageDrawable(drawable);
						}
					});

			if (image == null) {
				holder.img_wb_item_content_pic
						.setImageResource(R.drawable.detail_pic_loading);
			}
			else {
				holder.img_wb_item_content_pic.setImageDrawable(image);
			}
		}

		// 判断是否是转发
		if (s.getRetweeted_status() != null) {
			holder.lyt_wb_item_sublayout = (LinearLayout) view
					.findViewById(R.id.lyt_wb_item_sublayout);

			holder.lyt_wb_item_sublayout.setVisibility(View.VISIBLE);

			holder.txt_wb_item_subcontent = (TextView) view
					.findViewById(R.id.txt_wb_item_subcontent);
			if (s.getRetweeted_status().getText() != null)
				holder.txt_wb_item_subcontent.setText(StringUtil
						.getSpannableString(s.getRetweeted_status().getText()));

			// 判断微博中是否含有图片
			if (!StringUtil.isEmpty(s.getRetweeted_status().getThumbnail_pic())) {
				holder.img_wb_item_content_subpic = (ImageView) view
						.findViewById(R.id.img_wb_item_content_subpic);
				holder.img_wb_item_content_subpic.setVisibility(View.VISIBLE);

				Drawable image = AsynImageLoader.loadDrawable(s
						.getRetweeted_status().getThumbnail_pic(),
						holder.img_wb_item_content_subpic, new ImageCallback() {

							@Override
							public void imageSet(Drawable drawable,
									ImageView imageView) {

								// TODO Auto-generated method stub
								imageView.setImageDrawable(drawable);
							}
						});

				if (image == null) {
					holder.img_wb_item_content_subpic
							.setImageResource(R.drawable.detail_pic_loading);
				}
				else {
					holder.img_wb_item_content_subpic.setImageDrawable(image);
				}
			}

		}

		holder.txt_wb_item_comment = (TextView) view
				.findViewById(R.id.txt_wb_item_comment1);
		Log.v("com", "" + s.getComments_count());
		holder.txt_wb_item_comment.setText("" + s.getComments_count());

		holder.txt_wb_item_reposed = (TextView) view
				.findViewById(R.id.txt_wb_item_reposed);
		holder.txt_wb_item_reposed.setText("" + s.getReposts_count());

	}

	static class WeiboHolder {

		ImageView img_wb_item_head;

		TextView txt_wb_item_uname;

		ImageView img_wb_item_V;

		TextView txt_wb_item_time;

		TextView txt_wb_item_content;

		ImageView img_wb_item_content_pic;

		LinearLayout lyt_wb_item_sublayout;

		TextView txt_wb_item_subcontent;

		ImageView img_wb_item_content_subpic;

		TextView txt_wb_item_from;

		TextView txt_wb_item_reposed;

		TextView txt_wb_item_comment;

	}

}
