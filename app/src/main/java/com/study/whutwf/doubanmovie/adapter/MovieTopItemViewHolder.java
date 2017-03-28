package com.study.whutwf.doubanmovie.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.bean.MovieItem;

/**
 * Created by whutwf on 17-3-13.
 */

public class MovieTopItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView mMovieNameTextView;
    private TextView mMovieYearTextView;
    private TextView mMovieOriginalTitleTextView;
    private TextView mMovieScoreTextView;
    private ImageView mMovieCoverImageView;
    private AppCompatRatingBar mAppCompatRatingBar;
    private ImageView mOverflowMenuImageView;

    private MovieTopItemViewHolder.ClickResponseListener mClickResponseListener;

    public MovieTopItemViewHolder(View itemView, MovieTopItemViewHolder.ClickResponseListener clickResponseListener) {
        super(itemView);

        mClickResponseListener = clickResponseListener;

        mMovieNameTextView = (TextView) itemView.findViewById(R.id.top_movie_item_name);
        mMovieYearTextView = (TextView) itemView.findViewById(R.id.top_movie_item_date);
        mMovieCoverImageView = (ImageView) itemView.findViewById(R.id.top_movie_item_cover);
        mAppCompatRatingBar = (AppCompatRatingBar) itemView.findViewById(R.id.rating_bar_hots);
        mMovieOriginalTitleTextView = (TextView) itemView.findViewById(R.id.top_movie_item_original_name);
        mMovieScoreTextView = (TextView) itemView.findViewById(R.id.top_movie_item_score);
        mOverflowMenuImageView = (ImageView) itemView.findViewById(R.id.top_card_share_overflow);

        mOverflowMenuImageView.setOnClickListener(this);
        itemView.setOnClickListener(this);

    }

    public static MovieTopItemViewHolder newInstance(View parent, MovieTopItemViewHolder.ClickResponseListener clickResponseListener) {
        return new MovieTopItemViewHolder(parent, clickResponseListener);
    }

    public void setTopMovieItem(MovieItem movieItem) {
        mMovieNameTextView.setText(movieItem.getTitle());
        mMovieOriginalTitleTextView.setText(movieItem.getOriginalTitle());
        mMovieYearTextView.setText(movieItem.getYear());
        mMovieScoreTextView.setText(movieItem.getRating());
        mAppCompatRatingBar.setRating(Float.parseFloat(movieItem.getRating()) / 2);

    }

    public void setTopMovieCover(Drawable drawable) {
        mMovieCoverImageView.setImageDrawable(drawable);
    }

    @Override
    public void onClick(View view) {
        if (view == mOverflowMenuImageView) {
            mClickResponseListener.onOverflowClick(view, getAdapterPosition());
        } else {
            mClickResponseListener.onWholeClick(getAdapterPosition());
        }
    }

    /**
     * 注意接口的使用，可以针对不同的场景不同实现
     */
    public interface ClickResponseListener {
        void onWholeClick(int position);
        void onOverflowClick(View v ,int position);
    }
}