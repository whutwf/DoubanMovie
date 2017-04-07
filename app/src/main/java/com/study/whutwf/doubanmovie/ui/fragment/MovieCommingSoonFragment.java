package com.study.whutwf.doubanmovie.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.study.whutwf.doubanmovie.db.MovieItemDbSchema.MovieItemDb;
import com.study.whutwf.doubanmovie.service.PollService;
import com.study.whutwf.doubanmovie.support.Constants;
import com.study.whutwf.doubanmovie.support.SerializableHashMap;

/**
 * Created by whutwf on 17-3-29.
 */

public class MovieCommingSoonFragment extends MovieBaseFragment {

    public static final String TAG = "MovieCommingSoonFragment";

    private SerializableHashMap mSerializableHashMap = new SerializableHashMap();

    public MovieCommingSoonFragment() {

        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_COMING_SOON);

        mPageTag = MovieItemDb.DbBaseSettings.TABLE_COMING_SOON;
        mSerializableHashMap.setMap(paramsHashMap);
    }

    public static MovieCommingSoonFragment newInstance() {
        return new MovieCommingSoonFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = PollService.newIntent(getActivity());
        i.putExtra(Constants.ExtraIntentString.TARGET_ACTIVITY_NAME, TAG);
        i.putExtra(Constants.ExtraIntentString.PAGE_TAG, MovieItemDb.DbBaseSettings.TABLE_COMING_SOON);
        i.putExtra(Constants.ExtraIntentString.NOTIFIACTION_ID, Constants.NotificationId.MOVIE_COMMING_SOON);

        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.ExtraIntentString.S_HASH_MAP, mSerializableHashMap);
        i.putExtras(bundle);

        getActivity().startService(i);
    }

    @Override
    public void initView(View v) {
        super.initView(v);

        updateItems();
        setupAdapter();
        updatePageSettings();
    }
}
