package com.study.whutwf.doubanmovie.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.widget.RelativeLayout;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.ui.fragment.SearchMovieFragment;

/**
 * Created by whutwf on 17-3-28.
 */

public class SearchActivity extends BaseActivity {

    private SearchView mSearchView;
    private SearchMovieFragment mSearchMovieFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        mSearchMovieFragment = SearchMovieFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_frame, mSearchMovieFragment)
                .commit();
    }

    private void initView() {
        //这里有问题
        setTitle("");
        mSearchView = new SearchView(this);
        mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
        //默认展开
        mSearchView.setIconifiedByDefault(false);
//        mSearchView.
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.addView(mSearchView);

        mToolbar.addView(relativeLayout);
        setSupportActionBar(mToolbar);
        //这句话的作用,目前没有起作用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
