package com.study.whutwf.doubanmovie.ui.fragment;

import com.study.whutwf.doubanmovie.support.Constants;

/**
 * Created by whutwf on 17-3-29.
 */

public class MovieTopFragment extends MovieBaseFragment {

    public MovieTopFragment() {
        super();
        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_TOP250);
    }

    public static MovieTopFragment newInstance() {
        return new MovieTopFragment();
    }
}
