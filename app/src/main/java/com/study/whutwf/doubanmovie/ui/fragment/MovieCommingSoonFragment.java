package com.study.whutwf.doubanmovie.ui.fragment;

import com.study.whutwf.doubanmovie.support.Constants;

/**
 * Created by whutwf on 17-3-29.
 */

public class MovieCommingSoonFragment extends MovieBaseFragment {

    public MovieCommingSoonFragment() {
        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_COMING_SOON);
    }

    public static MovieCommingSoonFragment newInstance() {
        return new MovieCommingSoonFragment();
    }
}
