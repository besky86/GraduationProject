package com.weibo.sdk.android.demo;

import java.util.ArrayList;  
  
import android.app.ActivityGroup;  
import android.content.Intent;  
import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.graphics.drawable.BitmapDrawable;  
import android.os.Bundle;  
import android.support.v4.view.PagerAdapter;  
import android.support.v4.view.ViewPager;  
import android.support.v4.view.ViewPager.OnPageChangeListener;  
import android.view.Gravity;  
import android.view.View;  
import android.view.Window;  
import android.view.WindowManager;  
import android.view.View.OnClickListener;  
import android.widget.HorizontalScrollView;  
import android.widget.LinearLayout;  
import android.widget.RadioButton;  
import android.widget.RadioGroup;  
import android.widget.TextView;  
import android.widget.LinearLayout.LayoutParams;  
  
@SuppressWarnings("deprecation")
public class NewsActivity extends ActivityGroup implements  
        OnClickListener {  
    private RadioGroup radioGroup;  
  
    private String array[] = { "@我", "评论" };  
    private LinearLayout linearLayout;  
    private ArrayList<TextView> textViews;  
    private ViewPager viewPager;  
    private ArrayList<View> pageViews;  
    private HorizontalScrollView horizontalScrollView;  
  
    /*** 
     *初始化视图 
     *  
     *  
     */  
    void InItView() {  
        // 构造一个新的ArrayList实例对象   
        pageViews = new ArrayList<View>();  
        /** 
         * 　　开始一个新的活动中运行的组织。 每一个活动你开始必须有一个独一无二的字符串标识与其相关联 
         * **/  
        View view1 = getLocalActivityManager().startActivity("main1",  
                new Intent(this, AtMeActivity.class)).getDecorView();  
        View view2 = getLocalActivityManager().startActivity("main2",  
                new Intent(this, CommentToMeActivity.class)).getDecorView();  

        // 添加指定的对象在文章末尾的ArrayList。   
        pageViews.add(view1);  
        pageViews.add(view2);  
    }  
  
    /*** 
     * 初始化话标题栏 
     */  
    void InItTitle() {  
        // 获取窗口管理器显示自定义窗口,去掉默认显示对象。得到1/3屏幕宽度   
        int width = getWindowManager().getDefaultDisplay().getWidth() / 2;  
  
        for (int i = 0; i < array.length; i++) {  
            // 声明一个radioButton对象   
            RadioButton radioButton = new RadioButton(this, null);  
            radioButton.setText(array[i]);  
            radioButton.setWidth(width);  
            radioButton.setHeight(70);  
            // 集水平对齐文本和垂直重力的时候将会使用有额外的空间在TextView超出要求的文本本身   
            radioButton.setGravity(Gravity.CENTER);  
            // 添加子视图。如果没有布局参数对孩子已经设置,默认参数对于这个ViewGroup上设置的孩子。   
            radioGroup.addView(radioButton);  
        }  
    }  
  
    /*** 
     * 初始化文本 
     */  
    void initTextView() {  
        // 声明一个ArrayList对象   
        textViews = new ArrayList<TextView>();  
        // 获取窗口管理器显示自定义窗口,去掉默认显示对象。得到1/3屏幕宽度   
        int width = getWindowManager().getDefaultDisplay().getWidth() / 2;  
        int height = 80;  
        for (int i = 0; i < array.length; i++) {  
            TextView textView = new TextView(this);  
            textView.setText(array[i]);  
            textView.setTextSize(17);  
            textView.setWidth(width);  
            textView.setHeight(height - 10);  
            textView.setGravity(Gravity.CENTER);  
            textView.setId(i);  
            // 设置文本的监听事件   
            textView.setOnClickListener(this);  
            textViews.add(textView);  
            LinearLayout.LayoutParams layoutParams = new LayoutParams(  
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);  
            layoutParams.width = 1;  
            layoutParams.height = height - 40;  
            layoutParams.gravity = Gravity.CENTER;  
            // 添加子视图。如果没有布局参数对孩子已经设置,默认参数对于这个ViewGroup上设置的孩子。   
            linearLayout.addView(textView);  
  
        }  
    }  
  
    /*** 
     * 选中后的处理 
     */  
    public void select(int id) {  
        for (int i = 0; i < array.length; i++) {  
            if (id == i) {  
                // 得到图片的资源   
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),  
                        R.drawable.activity_selected);  
                // 设置背景图   
                
                textViews.get(id).setBackgroundDrawable(  
                        new BitmapDrawable(bitmap));  
                viewPager.setCurrentItem(i);  
            }  
  
            else {  
            	textViews.get(i).setBackgroundResource(R.drawable.title_button_group_middle_selected);
            }  
        }  
    }  
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        // 去除标题栏   
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
        // 取消状态栏，充满全屏   
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
  
        setContentView(R.layout.activity_news);  
        // 实例化组件   
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);  
        viewPager = (ViewPager) findViewById(R.id.viewpaper);  
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalscrollview);  
        // 调用方法实现   
        initTextView();  
        select(0);  
        InItView();  
  
        viewPager.setAdapter(new PagerAdapter() {  
            // 得到数目   
            public int getCount() {  
                return pageViews.size();  
            }  
  
            @Override  
            public boolean isViewFromObject(View view, Object object) {  
                return view == object;  
            }  
  
            @Override  
            public int getItemPosition(Object object) {  
                // TODO Auto-generated method stub   
                return super.getItemPosition(object);  
            }  
  
            @Override  
            public void destroyItem(View view, int id, Object arg2) {  
                // TODO Auto-generated method stub   
                ((ViewPager) view).removeView(pageViews.get(id));  
            }  
  
            // 获取每一个item的id   
            @Override  
            public Object instantiateItem(View view, int id) {  
                ((ViewPager) view).addView(pageViews.get(id));  
                return pageViews.get(id);  
            }  
  
        });  
        // 页面改变时候的监听事件   
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {  
  
            @Override  
            public void onPageSelected(int arg0) {  
                select(arg0);  
            }  
  
            @Override  
            public void onPageScrolled(int arg0, float arg1, int arg2) {  
  
            }  
  
            @Override  
            public void onPageScrollStateChanged(int arg0) {  
  
            }  
        });  
    }  
  
    @Override  
    public void onClick(View v) {  
        select(v.getId());  
    }  
  
}  