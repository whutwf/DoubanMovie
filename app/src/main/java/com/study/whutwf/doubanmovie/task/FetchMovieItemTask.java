package com.study.whutwf.doubanmovie.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.support.v7.widget.RecyclerView;

import com.study.whutwf.doubanmovie.adapter.MovieAdapter;
import com.study.whutwf.doubanmovie.bean.MovieBeanList;

import com.study.whutwf.doubanmovie.handler.MovieHandler;
import com.study.whutwf.doubanmovie.support.Constants;
import com.study.whutwf.doubanmovie.utils.FetchMovieItemUtils;

import java.util.HashMap;

/**
 * Created by whutw on 2017/3/21 0021.
 */

//注意参数这个位置Integer
public class FetchMovieItemTask extends AsyncTask<HashMap<String, String>, Void, MovieBeanList> {

    private MovieAdapter mMovieTopAdapter;

    private boolean mSend = false;
    private Handler mHandler;

    public FetchMovieItemTask(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mMovieTopAdapter = (MovieAdapter) adapter;
    }

    @Override
    protected MovieBeanList doInBackground(HashMap<String, String>... params) {
        MovieBeanList movieBeanList = new FetchMovieItemUtils().getMovieBeanList(params[0]);

        if (mSend == false) {
            Message message = new Message();
            MovieHandler handler = MovieHandler.getInstance();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.Params.DOUBAN_MOVIE_TOTAL, movieBeanList.getTotal());
            bundle.putInt(Constants.Params.DOUBAN_MOVIE_COUNT, movieBeanList.getCount());
            message.setData(bundle);
            handler.sendMessage(message);
            message.what = Constants.MessageId.MOVIE_PAGE_INFO;
            mSend = true;
        }
        return movieBeanList;
    }

    @Override
    protected void onPostExecute(MovieBeanList movieBeanList) {
        //在这里进行更新UI,使用notifyDataSetChanged()通知数据更新
        mMovieTopAdapter.addMovieItems(movieBeanList.getMovieItems());
        mMovieTopAdapter.notifyDataSetChanged();
    }
}
