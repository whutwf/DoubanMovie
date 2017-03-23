package com.study.whutwf.doubanmovie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.bean.MovieItem;

/**
 * Created by whutwf on 17-3-13.
 */

public class MovieTopItemViewHolder extends RecyclerView.ViewHolder {

    private TextView mMovieNameTextView;
    private TextView mMovieYearTextView;

    public MovieTopItemViewHolder(View itemView) {
        super(itemView);
        mMovieNameTextView = (TextView) itemView.findViewById(R.id.top_movie_item_name);
        mMovieYearTextView = (TextView) itemView.findViewById(R.id.top_movie_item_date);
    }

    public static MovieTopItemViewHolder newInstance(View parent) {
        return new MovieTopItemViewHolder(parent);
    }

    public void setTopMovieItem(MovieItem movieItem) {
        mMovieNameTextView.setText(movieItem.getTitle());
        mMovieYearTextView.setText(movieItem.getYear());
    }

}
