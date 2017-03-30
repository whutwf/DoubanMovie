package com.study.whutwf.doubanmovie.ui.fragment;

import android.view.View;

import com.study.whutwf.doubanmovie.support.Constants;

/**
 * Created by whutwf on 17-3-29.
 */

public class MovieInTheatersFragment extends MovieBaseFragment {

    public MovieInTheatersFragment() {
        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_IN_THEATERS);
    }

    public static MovieInTheatersFragment newInstance() {
        return new MovieInTheatersFragment();
    }

    @Override
    public void initView(View v) {
        super.initView(v);

        updateItems();
//        updatePageSettings();
    }
}
