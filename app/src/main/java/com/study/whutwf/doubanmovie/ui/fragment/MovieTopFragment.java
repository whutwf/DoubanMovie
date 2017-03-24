package com.study.whutwf.doubanmovie.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.bean.MovieItem;
import com.study.whutwf.doubanmovie.task.ImageDownloader;
import com.study.whutwf.doubanmovie.utils.FetchMovieItemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whutwf on 17-3-23.
 */

public class MovieTopFragment extends Fragment {

    private static final String TAG = "MovieTopFragment";

    private static final int END_START_PAGE = 13;

    private RecyclerView mMovieTopRecyclerView;
    private List<MovieItem> mMovieItemList = new ArrayList<>();
    private MovieTopAdapter mMovieTopAdapter;

    private  ImageDownloader<MovieTopItemViewHolder> mMovieTopItemViewHolderImageDownloader;
    private int mTopLastPositon;
    private int mTopStarPage = 0;
    private int mTopFetchedPage = 0;


    public static MovieTopFragment newInstance() {
        return new MovieTopFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchMovieItemTask().execute(mTopStarPage);

        Handler responseHandler = new Handler();

        mMovieTopItemViewHolderImageDownloader = new ImageDownloader<>(responseHandler);
        mMovieTopItemViewHolderImageDownloader.setImageDownloadListener(
                new ImageDownloader.ImageDownloadListener<MovieTopItemViewHolder>() {
                    @Override
                    public void onImageDownloaded(MovieTopItemViewHolder targetHolder, Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        targetHolder.setTopMovieCover(drawable);

                    }
                });
        mMovieTopItemViewHolderImageDownloader.start();
        mMovieTopItemViewHolderImageDownloader.getLooper();
        Log.i(TAG, "Background thread started");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movie_list, container, false);
        mMovieTopRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_movie_list_recycler_view);
        mMovieTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();
        //这个位置要注意啊，要返回自己的v，否则返回空视图
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMovieTopItemViewHolderImageDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMovieTopItemViewHolderImageDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    public  void setupAdapter() {
        if (isAdded()) {
            mMovieTopAdapter = new MovieTopAdapter(mMovieItemList);
            mMovieTopRecyclerView.setAdapter(mMovieTopAdapter);
            mMovieTopRecyclerView.addOnScrollListener(topMovieScrollListener);
        }
    }

    private RecyclerView.OnScrollListener topMovieScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            //首先获取LayoutManager
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //找到最后显示的位置，一旦滚动就会获得此位置
            mTopLastPositon = layoutManager.findLastVisibleItemPosition();

            //SCROLL_STATE_IDLE: 视图没有被拖动，处于静止
            //SCROLL_STATE_DRAGGING： 视图正在拖动中
            //SCROLL_STATE_SETTLING： 视图在惯性滚动
            if ((newState == RecyclerView.SCROLL_STATE_IDLE)
                    && (mTopLastPositon >= mMovieTopAdapter.getItemCount() - 1)
                    && (mTopStarPage == mTopFetchedPage - 1)) {
                Toast.makeText(getActivity(), "waiting to load ....", Toast.LENGTH_LONG).show();
                mTopStarPage++;
                if (mTopStarPage <= END_START_PAGE) {
                    Toast.makeText(getActivity(), "waiting to load ....", Toast.LENGTH_LONG).show();
                    new FetchMovieItemTask().execute(mTopStarPage);
                } else {
                    Toast.makeText(getActivity(), "This is the end ....", Toast.LENGTH_LONG).show();
                }

            }
        }
    };

    //注意参数这个位置Integer
    private class FetchMovieItemTask extends AsyncTask<Integer, Void, List<MovieItem>> {
        @Override
        protected List<MovieItem> doInBackground(Integer... params) {

            return new FetchMovieItemUtils().fetchMovieTop250Items(params[0]);
        }

        @Override
        protected void onPostExecute(List<MovieItem> movieItems) {
            if (mMovieItemList != null) {
                mMovieItemList.addAll(movieItems);
            } else {
                mMovieItemList = movieItems;
            }
            setupAdapter();
            mTopFetchedPage++;
        }
    }

    private class MovieTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

            mMovieTopItemViewHolderImageDownloader.queueTargetImage((MovieTopItemViewHolder) viewHolder, movieItem.getImageUrls().get(1));

        }

        @Override
        public int getItemCount() {
            return mMovieItemList == null ? 0 : mMovieItemList.size();
        }
    }

    private static class MovieTopItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mMovieNameTextView;
        private TextView mMovieYearTextView;
        private ImageView mMovieCoverImageView;

        public MovieTopItemViewHolder(View itemView) {
            super(itemView);
            mMovieNameTextView = (TextView) itemView.findViewById(R.id.top_movie_item_name);
            mMovieYearTextView = (TextView) itemView.findViewById(R.id.top_movie_item_date);
            mMovieCoverImageView = (ImageView) itemView.findViewById(R.id.top_movie_item_cover);
        }

        public static MovieTopItemViewHolder newInstance(View parent) {
            return new MovieTopItemViewHolder(parent);
        }

        public void setTopMovieItem(MovieItem movieItem) {
            mMovieNameTextView.setText(movieItem.getTitle());
            mMovieYearTextView.setText(movieItem.getYear());
        }

        public void setTopMovieCover(Drawable drawable) {
            mMovieCoverImageView.setImageDrawable(drawable);
        }

    }
}