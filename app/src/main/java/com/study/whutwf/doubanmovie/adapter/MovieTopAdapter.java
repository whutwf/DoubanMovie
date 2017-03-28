package com.study.whutwf.doubanmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.bean.MovieItem;
import com.study.whutwf.doubanmovie.support.Check;
import com.study.whutwf.doubanmovie.support.Constants;
import com.study.whutwf.doubanmovie.task.ImageDownloader;

import java.util.List;

/**
 * Created by whutwf on 17-3-13.
 */

public class MovieTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieItem> mMovieItemList;
    private ImageDownloader<MovieTopItemViewHolder> mMovieTopItemViewHolderImageDownloader;

//    public MovieTopAdapter(List<MovieItem> itemList, ImageDownloader<MovieTopItemViewHolder> imageDownloader) {
//        mMovieItemList = itemList;
//        mMovieTopItemViewHolderImageDownloader = imageDownloader;
//    }

    public MovieTopAdapter(List<MovieItem> movieItemList, ImageDownloader<MovieTopItemViewHolder> imageDownloader) {
        mMovieItemList = movieItemList;
        mMovieTopItemViewHolderImageDownloader = imageDownloader;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.top_movie_item, parent, false);
        return MovieTopItemViewHolder.newInstance(view, new MovieTopItemViewHolder.ClickResponseListener() {
            @Override
            public void onWholeClick(int position) {

                //这里的position是在调用onBindViewHolder才会赋值
                //是在页面渲染以后已经存在
                browerMovie(context, position);
            }

            @Override
            public void onOverflowClick(View v, final int position) {

                PopupMenu popupMenu = new PopupMenu(context, v);
                //Inflating the popup using xml file
                popupMenu.getMenuInflater()
                        .inflate(R.menu.pop_menu_movie_list, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    //这里的position是在调用onBindViewHolder才会赋值
                    //是在页面渲染以后已经存在
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        shareMovie(context, position);
                        return true;
                    }
                });
                //showing popup menu
                popupMenu.show();
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MovieTopItemViewHolder holder = (MovieTopItemViewHolder) viewHolder;
        MovieItem movieItem = mMovieItemList.get(position);
        holder.setTopMovieItem(movieItem);

        mMovieTopItemViewHolderImageDownloader.queueTargetImage((MovieTopItemViewHolder) viewHolder, movieItem.getImageUrls().get(1));

    }

    @Override
    public int getItemCount() {
        return mMovieItemList == null ? 0 : mMovieItemList.size();
    }

    private void browerMovie(Context context, int position) {
        MovieItem movieItem = mMovieItemList.get(position);
        openMovieDetails(context, movieItem.getAlt());
    }

    private void openMovieDetails(Context context, String url) {
        //===============有待完善当使用过一次客户端，是否标记
        if (Check.isApkInstalled(context, Constants.PackageInformation.DOUBAN_PACKAGE_NAME)) {
            openWithDouban(context, url);
        } else {
            openWithBrowser(context, url);
        }
    }

    private void openWithBrowser(Context context, String url) {
        Intent browerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        if (Check.isIntentAvailable(context, browerIntent)) {
            context.startActivity(browerIntent);
        } else {
            Toast.makeText(context, context.getString(R.string.no_browser), Toast.LENGTH_SHORT).show();
        }
    }

    private void openWithDouban(Context context, String url) {
        Intent doubanIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        doubanIntent.setPackage(Constants.PackageInformation.DOUBAN_PACKAGE_NAME);
        context.startActivity(doubanIntent);
    }

    private void shareMovie(Context context, int position) {
        MovieItem movieItem = mMovieItemList.get(position);
        shareMovie(context, movieItem.getTitle(), movieItem.getAlt());
    }

    private void shareMovie(Context context, String movieTitle, String movieUrl) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        //设置分享类别
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                movieTitle + " " + movieUrl + Constants.HelpStrings.SHARE_FROME_DOUBAN_MOVIE);
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.movie_share_to)));
    }

    private static class MovieTopItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

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
}