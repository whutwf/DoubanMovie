package com.study.whutwf.doubanmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.bean.MovieItem;

import java.util.List;

/**
 * Created by whutwf on 17-3-13.
 */

public class MovieTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieItem> mMovieItemList;

    public MovieTopAdapter(List<MovieItem> itemList) {
        mMovieItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.top_movie_item, parent, false);
        return MovieTopItemViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MovieTopItemViewHolder holder = (MovieTopItemViewHolder) viewHolder;
        MovieItem movieItem = mMovieItemList.get(position);
        holder.setTopMovieItem(movieItem);
    }

    @Override
    public int getItemCount() {
        return mMovieItemList == null ? 0 : mMovieItemList.size();
    }
}