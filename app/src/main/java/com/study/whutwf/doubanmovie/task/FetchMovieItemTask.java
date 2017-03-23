package com.study.whutwf.doubanmovie.task;

import android.os.AsyncTask;

import com.study.whutwf.doubanmovie.bean.MovieItem;
import com.study.whutwf.doubanmovie.utils.FetchMovieItemUtils;

import java.util.List;

/**
 * Created by whutw on 2017/3/21 0021.
 */

public class FetchMovieItemTask extends AsyncTask<Void, Void, List<MovieItem>> {
    @Override
    protected List<MovieItem> doInBackground(Void... params) {

        return new FetchMovieItemUtils().fetchMovieTop250Items();
    }

    @Override
    protected void onPostExecute(List<MovieItem> movieItems) {
        super.onPostExecute(movieItems);
    }
}
