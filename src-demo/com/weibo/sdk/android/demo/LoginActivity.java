package com.weibo.sdk.android.demo;

import java.text.SimpleDateFormat;
import java.util.List;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.entity.UserInfo;
import com.weibo.sdk.android.keep.AccessTokenKeeper;
import com.weibo.sdk.android.services.UserInfoService;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class LoginActivity extends Activity {
    private UserInfoService service;

    List<UserInfo> users = null;
    private ImageButton addAccountButton;
    private Button buttonLogin;
    private Button selectButton;
    private Weibo mWeibo;
    private static final String CONSUMER_KEY = "1325775247";// "966056985";//
                                                            // 替换为开发者的appkey，例如"1646212860";
    private static final String REDIRECT_URL = "http://open.weibo.com/apps/1325775247/privilege/oauth";

    public static Oauth2AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 清楚相应的应用Cookie
        // Add or Update by Lei@2013/04/22 UPD START
        // CookieSyncManager.createInstance(getApplicationContext());
        // CookieManager.getInstance().removeAllCookie();
        CookieSyncManager.createInstance(getApplicationContext());
        CookieManager.getInstance().removeAllCookie();
        // Add or Update by Lei@2013/04/22 UPD END

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = new UserInfoService(this);
        users = service.findAllUsers();

        mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
        addAccountButton = (ImageButton) findViewById(R.id.add_user_button);
        addAccountButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                mWeibo.authorize(LoginActivity.this, new AuthDialogListener());
            }

        });

        selectButton = (Button) findViewById(R.id.user_select_btn);
        selectButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                View viewDialog = View.inflate(LoginActivity.this,
                        R.layout.user_select_dialog, null);

                Dialog dialog = new Dialog(LoginActivity.this,
                        R.style.user_select_style);
                dialog.setContentView(viewDialog);

                dialog.show();

                ListView listView = (ListView) viewDialog
                        .findViewById(R.id.user_list);
                
                

            }

        });

        buttonLogin = (Button) findViewById(R.id.login_button);
        buttonLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }

        });

        if (null == users || users.isEmpty()) {
            buttonLogin.setEnabled(false);

        } else {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    class AuthDialogListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            String uid = values.getString("uid");
            LoginActivity.accessToken = new Oauth2AccessToken(token,
                    expires_in, uid);
            // added by Lei@2013/04/12 UPD END

            // delete by Lei@2013/04/12 DEL START
            // MainActivity.accessToken = new Oauth2AccessToken(token,
            // expires_in);
            // delete by Lei@2013/04/12 DEL END
            if (LoginActivity.accessToken.isSessionValid()) {
                AccessTokenKeeper.keepAccessToken(LoginActivity.this,
                        accessToken);

                // 授权之后清楚所有与应用相关的Cookie
                // Add or Update by Lei@2013/04/22 UPD START
                // CookieSyncManager.createInstance(getApplicationContext());
                // CookieManager.getInstance().removeAllCookie();
                CookieSyncManager.createInstance(getApplicationContext());
                CookieManager.getInstance().removeAllCookie();
                // Add or Update by Lei@2013/04/22 UPD END
                Toast.makeText(LoginActivity.this, "认证成功", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(LoginActivity.this,
                        TestActivity.class);
                LoginActivity.this.startActivity(intent);

            }
            // added by Lei@2013/04/19 UPD START
            // LoginActivity.accessToken = null;

            // added by Lei@2013/04/19 UPD END

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

}
