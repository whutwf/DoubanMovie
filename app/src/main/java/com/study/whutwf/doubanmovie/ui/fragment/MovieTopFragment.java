package com.study.whutwf.doubanmovie.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
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
import com.study.whutwf.doubanmovie.utils.FetchMovieItemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whutwf on 17-3-23.
 */

public class MovieTopFragment extends Fragment {

    private static final String TAG = "MovieTopFragment";

    private static final int END_START_PAGE = 240;
    private static final int BITMAP_CACHE_SIZE = 4 * 1024 * 1024;   //4MB,多少为合适？

    private RecyclerView mMovieTopRecyclerView;
    private List<MovieItem> mMovieItemList = new ArrayList<>();
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
        new FetchMovieItemTask().execute(mTopStarPage);

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
            mMovieTopAdapter = new MovieTopAdapter(mMovieItemList);
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
            Log.i(TAG, "Last postion:" + mTopLastPositon);

            //SCROLL_STATE_IDLE: 视图没有被拖动，处于静止
            //SCROLL_STATE_DRAGGING： 视图正在拖动中
            //SCROLL_STATE_SETTLING： 视图在惯性滚动
            if ((newState == RecyclerView.SCROLL_STATE_IDLE)
                    && (mTopLastPositon >= mMovieTopAdapter.getItemCount() - 1)) {
                mTopStarPage = mTopStarPage + 20;
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
        }
    }

    private class MovieTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<MovieItem> mMovieItemList;

        public MovieTopAdapter(List<MovieItem> itemList) {
            mMovieItemList = itemList;
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

        private ClickResponseListener mClickResponseListener;

        public MovieTopItemViewHolder(View itemView, ClickResponseListener clickResponseListener) {
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

        public static MovieTopItemViewHolder newInstance(View parent, ClickResponseListener clickResponseListener) {
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
