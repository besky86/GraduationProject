package com.weibo.sdk.android.demo;

import java.text.SimpleDateFormat;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.keep.AccessTokenKeeper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 授权操作
 * 
 * @author lei
 * 
 */

public class OAuthActivity extends Activity {
    private Weibo mWeibo;
    private static final String CONSUMER_KEY = "1325775247";// "966056985";//
                                                            // 替换为开发者的appkey，例如"1646212860";
    private static final String REDIRECT_URL = "http://open.weibo.com/apps/1325775247/privilege/oauth";// "http://www.sina.com";

    public static Oauth2AccessToken accessToken;
    private final String TAG = "OAuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.oauth);

        mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);

        View dialogView = View.inflate(this, R.layout.oauth_dialog, null);
        Dialog dialog = new Dialog(this, R.style.oauth_style);
        dialog.setContentView(dialogView);
        dialog.show();
        Button oauth_start = (Button) dialogView.findViewById(R.id.oauth_start);

        oauth_start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                mWeibo.authorize(OAuthActivity.this, new AuthDialogListener());
                // added by Lei@2013/04/13 UPD START
                // Intent intent = new Intent(MainActivity.this,
                // TestActivity.class);
                // startActivity(intent);
                Log.v(TAG, "onClick");

                //delete by Lei@2013/04/17 DEL START
                //Intent intent = new Intent(OAuthActivity.this,
                //        TestActivity.class);
                //startActivity(intent);
                //delete by Lei@2013/04/17 DEL END
                // added by Lei@2013/04/13 UPD END
                Toast.makeText(OAuthActivity.this, "Oauth", Toast.LENGTH_SHORT)
                        .show( );

            }

        });

    }

    class AuthDialogListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            String uid = values.getString("uid");
            // added by Lei@2013/04/12 UPD START
            // MainActivity.accessToken = new Oauth2AccessToken(token,
            // expires_in,uid);
            OAuthActivity.accessToken = new Oauth2AccessToken(token,
                    expires_in, uid);
            // added by Lei@2013/04/12 UPD END

            // delete by Lei@2013/04/12 DEL START
            // MainActivity.accessToken = new Oauth2AccessToken(token,
            // expires_in);
            // delete by Lei@2013/04/12 DEL END
            // delete by Lei@2013/04/17 DEL START
            // if (MainActivity.accessToken.isSessionValid()) {
            // String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            // .format(new java.util.Date(MainActivity.accessToken
            // .getExpiresTime()));
            // try {
            // Class sso = Class
            // .forName("com.weibo.sdk.android.api.WeiboAPI");//
            // 如果支持weiboapi的话，显示api功能演示入口按钮
            // } catch (ClassNotFoundException e) {
            // // e.printStackTrace();
            // Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");
            //
            // }
            // delete by Lei@2013/04/17 DEL END

            // added by Lei@2013/04/12 UPD END

            // delete by Lei@2013/04/12 DEL START
            // MainActivity.accessToken = new Oauth2AccessToken(token,
            // expires_in);
            // delete by Lei@2013/04/12 DEL END
            if (LoginActivity.accessToken.isSessionValid()) {

                AccessTokenKeeper.keepAccessToken(OAuthActivity.this,
                        accessToken);
                Toast.makeText(OAuthActivity.this, "认证成功", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onError(WeiboDialogError e) {
            Toast.makeText(getApplicationContext(),
                    "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "Auth cancel",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getApplicationContext(),
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * 下面两个注释掉的代码，仅当sdk支持sso时有效，
         */

    }

}
