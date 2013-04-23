package com.weibo.sdk.android.adapter;

import java.util.List;

import com.weibo.sdk.android.entity.UserInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<UserInfo> users;

    public UserListAdapter(Context context, List<UserInfo> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return users == null ? 0 : users.size();

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return users == null ? null : users.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return users.get(position).getId();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        return null;
    }

}
