package com.study.whutwf.doubanmovie.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.adapter.MovieAdapter;
import com.study.whutwf.doubanmovie.adapter.MovieItemViewHolder;
import com.study.whutwf.doubanmovie.support.Constants;
import com.study.whutwf.doubanmovie.task.FetchMovieItemTask;
import com.study.whutwf.doubanmovie.task.ImageDownloader;

import java.util.HashMap;

/**
 * Created by whutwf on 17-3-23.
 */

public class MovieBaseFragment extends Fragment {

    private static final String TAG = "MovieBaseFragment";

    protected static final int END_START_PAGE = 240;
    protected static final int BITMAP_CACHE_SIZE = 4 * 1024 * 1024;   //4MB,多少为合适？

    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mMovieAdapter;

    private  ImageDownloader<MovieItemViewHolder> mMovieItemViewHolderImageDownloader;
    private int mStarPage = 0;

    protected HashMap<String, String> paramsHashMap = new HashMap<>();
    private LruCache<String, Bitmap> mBitmapLruCache;


    public MovieBaseFragment() {
        paramsHashMap.put("url", "");
        paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_START, "");
        paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_TAG, "");
        paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_COUNT, "");
        paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_QUERY, "");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (paramsHashMap.get("url") != "") {

            Handler responseHandler = new Handler();

            mBitmapLruCache = new LruCache<>(BITMAP_CACHE_SIZE);
            mMovieItemViewHolderImageDownloader = new ImageDownloader<>(responseHandler, mBitmapLruCache);
            mMovieItemViewHolderImageDownloader.setImageDownloadListener(
                    new ImageDownloader.ImageDownloadListener<MovieItemViewHolder>() {
                        @Override
                        public void onImageDownloaded(MovieItemViewHolder targetHolder, Bitmap bitmap) {
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            targetHolder.setTopMovieCover(drawable);

                        }
                    });
            mMovieItemViewHolderImageDownloader.start();
            mMovieItemViewHolderImageDownloader.getLooper();

            //转屏保持状态
            setRetainInstance(true);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movie_list, container, false);

        if (paramsHashMap.get("url") != "") {
            mMovieRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_movie_list_recycler_view);
            mMovieRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mMovieRecyclerView.addOnScrollListener(movieScrollListener);

            mMovieAdapter = new MovieAdapter(mMovieItemViewHolderImageDownloader);

            paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_START, String.valueOf(mStarPage));
            new FetchMovieItemTask(mMovieAdapter).execute(paramsHashMap);
            setupAdapter();
        }

        //这个位置要注意啊，要返回自己的v，否则返回空视图
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMovieItemViewHolderImageDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMovieItemViewHolderImageDownloader.quit();
    }

    public  void setupAdapter() {
        if (isAdded()) {
            mMovieRecyclerView.setAdapter(mMovieAdapter);
        }
    }



    private RecyclerView.OnScrollListener movieScrollListener = new RecyclerView.OnScrollListener() {

        private int lastOffset;
        private int lastPosition;
        private int mLastPositon;

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
            mLastPositon = layoutManager.findLastVisibleItemPosition();
            getPositionAndOffset();

            //SCROLL_STATE_IDLE: 视图没有被拖动，处于静止
            //SCROLL_STATE_DRAGGING： 视图正在拖动中
            //SCROLL_STATE_SETTLING： 视图在惯性滚动
            if ((newState == RecyclerView.SCROLL_STATE_IDLE)
                    && (mLastPositon >= mMovieAdapter.getItemCount() - 1)) {
                mStarPage = mStarPage + 20;
                if (mStarPage <= END_START_PAGE) {
                    Toast.makeText(getActivity(), "waiting to load ....", Toast.LENGTH_SHORT).show();
                    paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_START, String.valueOf(mStarPage));
                    new FetchMovieItemTask(mMovieAdapter).execute(paramsHashMap);
                    scrollToPosition();
                } else {
                    Toast.makeText(getActivity(), "This is the end ....", Toast.LENGTH_SHORT).show();
                }

            }
        }

        /**
         * 记录RecyclerView滚动位置并恢复
         */
        /**
         * 记录RecyclerView当前位置
         */
        private void getPositionAndOffset() {
            LinearLayoutManager layoutManager = (LinearLayoutManager) mMovieRecyclerView.getLayoutManager();
            //获取可视的第一个view
            View topView = layoutManager.getChildAt(0);
            if(topView != null) {
                //获取与该view的顶部的偏移量
                lastOffset = topView.getTop();
                //得到该View的数组位置
                lastPosition = layoutManager.getPosition(topView);
            }
        }

        /**
         * 让RecyclerView滚动到指定位置
         */
        private void scrollToPosition() {
            if(mMovieRecyclerView.getLayoutManager() != null && lastPosition >= 0) {
                ((LinearLayoutManager) mMovieRecyclerView.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
            }
        }

    };


}
