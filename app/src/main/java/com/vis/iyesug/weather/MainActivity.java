package com.vis.iyesug.weather;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.vis.iyesug.weather.presenter.ViewPagerAdapter;
import com.vis.iyesug.weather.util.SnackbarUtil;
import com.vis.iyesug.weather.util.Util;
import com.vis.iyesug.weather.view.RecyclerFragment;
import com.vis.iyesug.weather.view.WaitDialog;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private DrawerLayout mDrawerl;
    private CoordinatorLayout mCoordinatorl;
    private AppBarLayout mAppbarl;
    private Toolbar mToolbar;
    private TabLayout mTabl;
    private ViewPager mViewpager;
    private FloatingActionButton mFloating;
    private NavigationView mNavigation;

    private String [] mTitles;
    private List<Fragment> mFragments;
    private ViewPagerAdapter mViewpageradapter;
    private WaitDialog mWaitDialog;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initdata();
        setView();
        System.out.println("________________MainActivity");

    }


    private void initView() {
        mDrawerl= (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mCoordinatorl= (CoordinatorLayout) findViewById(R.id.id_coordinatorlayout);
        mAppbarl= (AppBarLayout) findViewById(R.id.id_appbarlayout);
        mTabl= (TabLayout) findViewById(R.id.id_tablayout);
        mToolbar= (Toolbar) findViewById(R.id.id_toolbar);
        mViewpager= (ViewPager) findViewById(R.id.id_viewpager);
        mFloating= (FloatingActionButton) findViewById(R.id.id_floatingactionbutton);
        mNavigation= (NavigationView) findViewById(R.id.id_navigationview);
        mWaitDialog = new WaitDialog(this);
    }


    private void initdata() {
        mTitles=getResources().getStringArray(R.array.titles);
        mFragments=new ArrayList<>();
        for(int i=0;i<mTitles.length;i++){
            Bundle mBundle=new Bundle();
            mBundle.putInt("flag",i);
            RecyclerFragment fragment=new RecyclerFragment();
            fragment.setArguments(mBundle);
            mFragments.add(i,fragment);
        }
    }



    private void setView() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mDrawerl,mToolbar,R.string.open,R.string.close);
        toggle.syncState();
        mDrawerl.setDrawerListener(toggle);

        mNavigation.inflateHeaderView(R.layout.header);
        mNavigation.inflateMenu(R.menu.menu);
        itemSelected(mNavigation);

        mViewpageradapter=new ViewPagerAdapter(getSupportFragmentManager(),mFragments,mTitles);

        mViewpager.setAdapter(mViewpageradapter);
        mViewpager.setOffscreenPageLimit(6);
        mViewpager.addOnPageChangeListener(this);

        mTabl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabl.setupWithViewPager(mViewpager);
       // mTabl.setTabsFromPagerAdapter(mViewpageradapter);
        mFloating.setOnClickListener(this);

    }
    private void itemSelected(NavigationView mNav) {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {

                String msgString = "";

                switch (menuItem.getItemId()) {
                    case R.id.nav_menu_home:
                        msgString = (String) menuItem.getTitle();
                        break;
                    case R.id.nav_menu_categories:


                        requestQueue = NoHttp.newRequestQueue();
                        // 创建请求对象。
                        Request<String> request = NoHttp.createStringRequest(Util.URL, RequestMethod.POST);

                        // 添加请求参数。
                        request.add("userName", "yolanda");
                        request.add("userPass", 1);
                        request.add("userAge", 1.25);

                        request.setConnectTimeout(NoHttp.getDefaultConnectTimeout());
                        request.setReadTimeout(NoHttp.getDefaultReadTimeout());



                        // 请求头，是否要添加头，添加什么头，要看开发者服务器端的要求。
                        request.addHeader("Author", "sample");

                        // 设置一个tag, 在请求完(失败/成功)时原封不动返回; 多数情况下不需要。
                        request.setTag(this);

                        // 设置取消标志。
                        request.setCancelSign(this);

		/*
         * what: 当多个请求同时使用同一个OnResponseListener时用来区分请求, 类似handler的what一样。
		 * request: 请求对象。
		 * onResponseListener 回调对象，接受请求结果。
		 */
                        requestQueue.add(1, request, onResponseListener);


                        break;
                    case R.id.nav_menu_feedback:
                        msgString = (String) menuItem.getTitle();
                        break;
                    case R.id.nav_menu_setting:
                        msgString = (String) menuItem.getTitle();
                        break;
                }


                menuItem.setChecked(true);
                mDrawerl.closeDrawers();

                SnackbarUtil.show(mViewpager, msgString, 0);

                return true;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // FloatingActionButton的点击事件
            case R.id.id_floatingactionbutton:
                SnackbarUtil.show(v, getString(R.string.dot), 0);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    /**
     * 回调对象，接受请求结果.
     */
    private OnResponseListener<String> onResponseListener = new OnResponseListener<String>() {
        @SuppressWarnings("unused")
        @Override
        public void onSucceed(int what, Response<String> response) {
            if (what == what) {// 根据what判断是哪个请求的返回，这样就可以用一个OnResponseListener来接受多个请求的结果。
                int responseCode = response.getHeaders().getResponseCode();// 服务器响应码。

                if (responseCode == 200) {// 如果是是用NoHttp的默认的请求或者自己没有对NoHttp做封装，这里最好判断下Http状态码。
                    String result = response.get();// 响应结果。


                    Object tag = response.getTag();// 拿到请求时设置的tag。
                    byte[] responseBody = response.getByteArray();// 如果需要byteArray自己解析的话。

                    // 响应头
                    Headers headers = response.getHeaders();
                    String headResult = getString(R.string.request_original_result);
                    headResult = String.format(Locale.getDefault(), headResult, headers.getResponseCode(), response.getNetworkMillis());
                    SnackbarUtil.show(mViewpager, result, 0);
                    SnackbarUtil.show(mViewpager, headResult, 0);
                }
            }
        }

        @Override
        public void onStart(int what) {
            // 请求开始，这里可以显示一个dialog
            if (mWaitDialog != null && !mWaitDialog.isShowing())
                mWaitDialog.show();
        }

        @Override
        public void onFinish(int what) {
            // 请求结束，这里关闭dialog
            if (mWaitDialog != null && mWaitDialog.isShowing())
                mWaitDialog.dismiss();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            // 请求失败

            SnackbarUtil.show(mViewpager, "请求失败: " + exception.getMessage(), 0);
        }
    };
}
