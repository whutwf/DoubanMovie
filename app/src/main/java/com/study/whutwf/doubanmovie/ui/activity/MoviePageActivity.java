package com.study.whutwf.doubanmovie.ui.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.ui.fragment.MoviePageFragment;
import com.study.whutwf.doubanmovie.ui.fragment.MovieSearchFragment;

public class MoviePageActivity extends BaseActivity {

    private MoviePageFragment mMoviePageFragment;

    public static Intent newIntent(Context context, Uri moviePageUri) {
        Intent i = new Intent(context, MoviePageActivity.class);
        //添加Uri方式
        i.setData(moviePageUri);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getIntent获取当前intent
        mMoviePageFragment = MoviePageFragment.newInstance(getIntent().getData());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_frame, mMoviePageFragment)
                .commit();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        //这里有问题
        setTitle("");
        //这句话的作用,目前没有起作用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initView() {
        super.initView();

    }

}
