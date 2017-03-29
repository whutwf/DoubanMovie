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
import com.study.whutwf.doubanmovie.adapter.MovieTopAdapter;
import com.study.whutwf.doubanmovie.adapter.MovieTopItemViewHolder;
import com.study.whutwf.doubanmovie.task.FetchMovieItemTask;
import com.study.whutwf.doubanmovie.task.ImageDownloader;

/**
 * Created by whutwf on 17-3-23.
 */

public class MovieTopFragment extends Fragment {

    private static final String TAG = "MovieTopFragment";

    private static final int END_START_PAGE = 240;
    private static final int BITMAP_CACHE_SIZE = 4 * 1024 * 1024;   //4MB,多少为合适？

    private RecyclerView mMovieTopRecyclerView;
    private MovieTopAdapter mMovieTopAdapter;

    private  ImageDownloader<MovieTopItemViewHolder> mMovieTopItemViewHolderImageDownloader;
    private int mTopLastPositon;
    private int mTopStarPage = 0;
    private int lastOffset;
    private int lastPosition;
    private LruCache<String, Bitmap> mBitmapLruCache;


    public static MovieTopFragment newInstance() {
        return new MovieTopFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler responseHandler = new Handler();

        mBitmapLruCache = new LruCache<>(BITMAP_CACHE_SIZE);
        mMovieTopItemViewHolderImageDownloader = new ImageDownloader<>(responseHandler, mBitmapLruCache);
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

        //转屏保持状态
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movie_list, container, false);

        mMovieTopRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_movie_list_recycler_view);
        mMovieTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMovieTopRecyclerView.addOnScrollListener(topMovieScrollListener);

        mMovieTopAdapter = new MovieTopAdapter(mMovieTopItemViewHolderImageDownloader);
        new FetchMovieItemTask(mMovieTopAdapter).execute(mTopStarPage);
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
    }

    public  void setupAdapter() {
        if (isAdded()) {
            mMovieTopRecyclerView.setAdapter(mMovieTopAdapter);
            scrollToPosition();
        }
    }


    /**
     * 记录RecyclerView滚动位置并恢复
     */
    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mMovieTopRecyclerView.getLayoutManager();
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
        if(mMovieTopRecyclerView.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) mMovieTopRecyclerView.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
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
            getPositionAndOffset();

            //SCROLL_STATE_IDLE: 视图没有被拖动，处于静止
            //SCROLL_STATE_DRAGGING： 视图正在拖动中
            //SCROLL_STATE_SETTLING： 视图在惯性滚动
            if ((newState == RecyclerView.SCROLL_STATE_IDLE)
                    && (mTopLastPositon >= mMovieTopAdapter.getItemCount() - 1)) {
                mTopStarPage = mTopStarPage + 20;
                if (mTopStarPage <= END_START_PAGE) {
                    Toast.makeText(getActivity(), "waiting to load ....", Toast.LENGTH_SHORT).show();
                    new FetchMovieItemTask(mMovieTopAdapter).execute(mTopStarPage);
                } else {
                    Toast.makeText(getActivity(), "This is the end ....", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };


}
