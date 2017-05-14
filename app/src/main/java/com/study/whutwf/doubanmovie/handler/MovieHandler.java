package com.study.whutwf.doubanmovie.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.study.whutwf.doubanmovie.support.Constants;
import com.study.whutwf.doubanmovie.ui.fragment.MovieBaseFragment;

/**
 * Created by whutw on 2017/5/14 0014.
 */

public class MovieHandler extends Handler {
    private static MovieHandler instance = null;

    public static MovieHandler getInstance() {
        if (instance == null) {
            instance = new MovieHandler();
        }

        return instance;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case Constants.MessageId.MOVIE_PAGE_INFO:
                Bundle bundle = msg.getData();
                MovieBaseFragment.END_START_PAGE = bundle.getInt(Constants.Params.DOUBAN_MOVIE_TOTAL);
                MovieBaseFragment.COUNT_EVE_PAGE = bundle.getInt(Constants.Params.DOUBAN_MOVIE_COUNT);
                break;
            default:
                break;
        }
    }
}
