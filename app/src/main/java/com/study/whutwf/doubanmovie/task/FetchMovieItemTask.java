package com.study.whutwf.doubanmovie.task;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.study.whutwf.doubanmovie.adapter.MovieAdapter;
import com.study.whutwf.doubanmovie.bean.MovieItem;
import com.study.whutwf.doubanmovie.utils.FetchMovieItemUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by whutw on 2017/3/21 0021.
 */

//注意参数这个位置Integer
public class FetchMovieItemTask extends AsyncTask<HashMap<String, String>, Void, List<MovieItem>> {

    private MovieAdapter mMovieTopAdapter;
    private Context mContext;
    private String mPageTag;

    public FetchMovieItemTask(Context context, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, String tag) {
        this.mMovieTopAdapter = (MovieAdapter) adapter;
        this.mContext = context;
        this.mPageTag = tag;
    }

    @Override
    protected List<MovieItem> doInBackground(HashMap<String, String>... params) {

        return new FetchMovieItemUtils(mContext, mPageTag).fetchMovieItems(params[0]);
    }

    @Override
    protected void onPostExecute(List<MovieItem> movieItems) {
        //在这里进行更新UI,使用notifyDataSetChanged()通知数据更新
        mMovieTopAdapter.addMovieItems(movieItems);
        mMovieTopAdapter.notifyDataSetChanged();
    }
}
