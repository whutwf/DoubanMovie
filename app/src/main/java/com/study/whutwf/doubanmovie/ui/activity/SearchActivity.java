package com.study.whutwf.doubanmovie.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.ui.fragment.MovieSearchFragment;

/**
 * Created by whutwf on 17-3-28.
 */

public class SearchActivity extends BaseActivity {

    private SearchView mSearchView;
    private MovieSearchFragment mSearchNewsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchNewsFragment = MovieSearchFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_frame, mSearchNewsFragment)
                .commit();
    }

    @Override
    protected void initView() {
        super.initView();
        //这里有问题
        setTitle("");
        //这句话的作用,目前没有起作用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
