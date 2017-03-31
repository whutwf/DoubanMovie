package com.study.whutwf.doubanmovie.ui.fragment;

import android.view.View;

import com.study.whutwf.doubanmovie.db.MovieItemDbSchema.MovieItemDb;
import com.study.whutwf.doubanmovie.support.Constants;

/**
 * Created by whutwf on 17-3-29.
 */

public class MovieTopFragment extends MovieBaseFragment {

    public MovieTopFragment() {

        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_TOP250);

        mPageTag = MovieItemDb.DbBaseSettings.TABLE_TOP250;
    }

    public static MovieTopFragment newInstance() {
        return new MovieTopFragment();
    }

    @Override
    public void initView(View v) {
        super.initView(v);
        updateItems();
        setupAdapter();
        updatePageSettings();

    }
}
