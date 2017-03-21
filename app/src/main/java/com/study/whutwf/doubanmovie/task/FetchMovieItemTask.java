package com.study.whutwf.doubanmovie.task;

import android.os.AsyncTask;
import android.util.Log;

import com.study.whutwf.doubanmovie.utils.NetworkUtils;

import java.io.IOException;

/**
 * Created by whutw on 2017/3/21 0021.
 */

public class FetchMovieItemTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        try {
            String result = NetworkUtils.getUrlString("https://www.baidu.com");
            Log.i("Baidu", "Fetch content of UTL: " + result);
        } catch (IOException e) {
            Log.e("Baidu", "Fail to fetch URL:", e);
            e.printStackTrace();
        }

        return null;
    }
}
