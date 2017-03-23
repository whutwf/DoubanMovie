package com.study.whutwf.doubanmovie.ui.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private RecyclerView mMovieTopRecyclerView;
    private List<MovieItem> mMovieItemList = new ArrayList<>();

    private  ImageDownloader<MovieTopItemViewHolder> mMovieTopItemViewHolderImageDownloader;

    public static MovieTopFragment newInstance() {
        return new MovieTopFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchMovieItemTask().execute();

        mMovieTopItemViewHolderImageDownloader = new ImageDownloader<>();
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
    public void onDestroy() {
        super.onDestroy();
        mMovieTopItemViewHolderImageDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    public  void setupAdapter() {
        if (isAdded()) {
            MovieTopAdapter recyclerAdapter = new MovieTopAdapter(mMovieItemList);
            mMovieTopRecyclerView.setAdapter(recyclerAdapter);
        }
    }

    private class FetchMovieItemTask extends AsyncTask<Void, Void, List<MovieItem>> {
        @Override
        protected List<MovieItem> doInBackground(Void... params) {

            return new FetchMovieItemUtils().fetchMovieTop250Items();
        }

        @Override
        protected void onPostExecute(List<MovieItem> movieItems) {
            mMovieItemList = movieItems;
            setupAdapter();
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

            mMovieTopItemViewHolderImageDownloader.queueTargetImage((MovieTopItemViewHolder) viewHolder, movieItem.getImageUrls().get(2));

        }

        @Override
        public int getItemCount() {
            return mMovieItemList == null ? 0 : mMovieItemList.size();
        }
    }

    private static class MovieTopItemViewHolder extends RecyclerView.ViewHolder {

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
}
