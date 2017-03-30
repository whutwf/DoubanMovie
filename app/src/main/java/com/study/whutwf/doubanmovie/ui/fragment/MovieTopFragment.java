package com.study.whutwf.doubanmovie.ui.fragment;

import android.util.Log;
import android.view.View;

import com.study.whutwf.doubanmovie.support.Constants;
import com.study.whutwf.doubanmovie.utils.QueryPreferencesUtils;

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

    @Override
    public void initView(View v) {
        super.initView(v);


        updateItems();
//                updatePageSettings();

        Log.i("MovieBeFragment", "Sign" + QueryPreferencesUtils.getSignStoredPreference(getContext(),
                Constants.Preferences.PAGE_SETTINGS_SIGN));



    }
}
