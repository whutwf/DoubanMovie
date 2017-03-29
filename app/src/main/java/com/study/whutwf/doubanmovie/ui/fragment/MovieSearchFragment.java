package com.study.whutwf.doubanmovie.ui.fragment;

import com.study.whutwf.doubanmovie.support.Constants;

/**
 * Created by whutwf on 17-3-28.
 */

public class MovieSearchFragment extends MovieBaseFragment {

    public MovieSearchFragment() {
        super();
        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_TOP250);
    }

    public static MovieSearchFragment newInstance() {
        return new MovieSearchFragment();
    }
}
