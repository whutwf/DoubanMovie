package com.study.whutwf.doubanmovie.task;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.study.whutwf.doubanmovie.adapter.MovieTopAdapter;
import com.study.whutwf.doubanmovie.bean.MovieItem;
import com.study.whutwf.doubanmovie.utils.FetchMovieItemUtils;

import java.util.List;

/**
 * Created by whutw on 2017/3/21 0021.
 */

//注意参数这个位置Integer
public class FetchMovieItemTask extends AsyncTask<Integer, Void, List<MovieItem>> {

    private MovieTopAdapter mMovieTopAdapter;

    public FetchMovieItemTask(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mMovieTopAdapter = (MovieTopAdapter) adapter;
    }

    @Override
    protected List<MovieItem> doInBackground(Integer... params) {

        return new FetchMovieItemUtils().fetchMovieTop250Items(params[0]);
    }

    @Override
    protected void onPostExecute(List<MovieItem> movieItems) {
        //在这里进行更新UI,使用notifyDataSetChanged()通知数据更新
        mMovieTopAdapter.addMovieItems(movieItems);
        mMovieTopAdapter.notifyDataSetChanged();
    }
}
