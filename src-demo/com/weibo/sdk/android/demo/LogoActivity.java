package com.weibo.sdk.android.demo;

import com.weibo.sdk.android.Oauth2AccessToken;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

public class LogoActivity extends Activity {

    private static final String PREFERENCES_NAME = "firstuse";
    public static String TAG = "LogoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.v(TAG, "全屏");
        // 全屏显示
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);

        ImageView imageLogo = (ImageView) this.findViewById(R.id.img_logo);

        // 设置渐变动画
        Log.v(TAG, "渐变");
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(1000);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                // Intent intent = new Intent(LogoActivity.this,
                // LoginActivity.class);
                Log.v(TAG, "onAnimationEnd（）");

                Intent intent = new Intent(LogoActivity.this,
                        LoginActivity.class);

                startActivity(intent);
                LogoActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

                // Tools.isNetworkAvailable(LogoActivity.this);
                // Toast

            }

        });

        imageLogo.setAnimation(animation);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logo, menu);
        return true;
    }

}
