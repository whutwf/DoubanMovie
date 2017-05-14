package com.study.whutwf.doubanmovie.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.study.whutwf.doubanmovie.db.MovieItemDbSchema.MovieItemDb;
import com.study.whutwf.doubanmovie.service.PollService;
import com.study.whutwf.doubanmovie.support.Constants;
import com.study.whutwf.doubanmovie.support.SerializableHashMap;

/**
 * Created by whutwf on 17-3-29.
 */

public class MovieTopFragment extends MovieBaseFragment {

    public static final String TAG = "MovieTopFragment";

    private SerializableHashMap mSerializableHashMap = new SerializableHashMap();

    public MovieTopFragment() {

        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_TOP250);

        mPageTag = MovieItemDb.DbBaseSettings.TABLE_TOP250;

        mSerializableHashMap.setMap(paramsHashMap);

    }

    public static MovieTopFragment newInstance() {
        return new MovieTopFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = PollService.newIntent(getActivity());
        i.putExtra(Constants.ExtraIntentString.TARGET_ACTIVITY_NAME, TAG);
        i.putExtra(Constants.ExtraIntentString.PAGE_TAG, MovieItemDb.DbBaseSettings.TABLE_TOP250);
        i.putExtra(Constants.ExtraIntentString.NOTIFIACTION_ID, Constants.NotificationId.MOVIE_TOP_250);

        //使用bundle传递序列化对象(HashMap)
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constants.ExtraIntentString.S_HASH_MAP, mSerializableHashMap);
        i.putExtras(bundle);

        getActivity().startService(i);
//        ServiceAlarm.setServiceAlarm(getContext(), i, true);
    }

    @Override
    public void initView(View v) {
        super.initView(v);
        updateItems();
        setupAdapter();
        Log.i(TAG, END_START_PAGE + "");
    }
}
