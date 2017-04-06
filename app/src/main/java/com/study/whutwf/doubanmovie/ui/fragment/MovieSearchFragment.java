package com.study.whutwf.doubanmovie.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.db.MovieItemDbSchema.MovieItemDb;
import com.study.whutwf.doubanmovie.support.Constants;

/**
 * Created by whutwf on 17-3-28.
 */

public class MovieSearchFragment extends MovieBaseFragment {

    public static final String TAG = "MovieSearchFragment";

    public MovieSearchFragment() {
        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_SEARCH);

        mPageTag = MovieItemDb.DbBaseSettings.TABLE_SEARCH;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        Intent i = PollService.newIntent(getActivity());
//        i.putExtra(Constants.ExtraIntentString.TARGET_ACTIVITY_NAME, TAG);
//        i.putExtra(Constants.ExtraIntentString.PAGE_TAG, MovieItemDb.DbBaseSettings.TABLE_SEARCH);
//        getActivity().startService(i);
    }

    public static MovieSearchFragment newInstance() {
        return new MovieSearchFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_movie, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_movie_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

//        AppCompatImageView button =(AppCompatImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_button);

//        button.setImageResource(R.mipmap.ic_search);

        //搜索的那个X的删除图标也是可以通过这种方式来修改的

        //下面是在搜索栏的字体，设置为白色，默认也是黑色

        TextView textView=(TextView)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        textView.setTextColor(Color.WHITE);
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_QUERY, query);

                //是因为存储电影数据的问题，否则就一直在往里面加数据，不是自己想要的
                //重新搜索清空电影列表
                mMovieAdapter.clearMovieItems();

                updateItems();
                setupAdapter();
                updatePageSettings();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

    }
}
