package com.study.whutwf.doubanmovie.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.study.whutwf.doubanmovie.R;

public class BaseActivity extends AppCompatActivity {
    private CoordinatorLayout mCoordinartorLayout;

    protected Toolbar mToolbar;
    protected int mLayoutResId = R.layout.activity_base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        super.onCreate(savedInstanceState);
        setContentView(mLayoutResId);

        initToolbar();
        initView();
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    protected void initView() {
        mCoordinartorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
    }

    //=========关注点Snackbar================
    public void showSnackbar(int resId) {
        Snackbar.make(mCoordinartorLayout, resId, Snackbar.LENGTH_SHORT).show();
    }
}
