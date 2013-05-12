/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weibo.sdk.android.adapter;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import com.weibo.sdk.android.demo.LoginActivity;
import com.weibo.sdk.android.demo.R;
import com.weibo.sdk.android.entity.UserInfo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter showing the types of items that can be added to a {@link Workspace}. <br>
 * 接口，用于显示项目的类型，可以添加到{@link Workspace}中的项目�?
 */
public class UserInfoListAdapter extends BaseAdapter {

	private final LayoutInflater mInflater;

	private ArrayList<UserInfo> mItems = new ArrayList<UserInfo>();

	public UserInfoListAdapter(LoginActivity login, List<UserInfo> users) {
		super();

		/**
		 * 以下两种方式均可以：
		 */
		// mInflater = getLayoutInflater(); // 在Activity里面才行
		// LayoutInflater inflater = LayoutInflater.from(context);// 也行�?
		mInflater = (LayoutInflater) login
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Create default actions

		for (UserInfo user : users) {
			mItems.add(user);
		}

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		UserInfo item = (UserInfo) getItem(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.user_add_list_item,
					parent, false);
		}

		TextView textView = (TextView) convertView;
		textView.setTextColor(Color.BLACK);
		textView.setTag(item);
		textView.setText(item.getUserName());
		// .........................

		// textView.setCompoundDrawablesWithIntrinsicBounds(item.image, null,
		// null, null);

		return convertView;
	}
	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		UserInfo item = (UserInfo) getItem(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.user_drop_list_item,
					parent, false);
		}

		TextView textView = (TextView) convertView;
		textView.setTextColor(Color.BLACK);
		textView.setTag(item);

		textView.setText(item.getUserName());
		textView.setCompoundDrawablesWithIntrinsicBounds(item.getUserIcon(),
				null, null, null);

		return textView;
	}

}
