package com.study.whutwf.doubanmovie.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.widget.RelativeLayout;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.ui.fragment.MovieSearchFragment;

/**
 * Created by whutwf on 17-3-28.
 */

public class SearchActivity extends SingleFragmentActivity {

    private SearchView mSearchView;

    @Override
    protected void initView() {
        super.initView();
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

    @Override
    protected Fragment createFragment() {
        return MovieSearchFragment.newInstance();
    }
}
