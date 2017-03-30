package com.study.whutwf.doubanmovie.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.support.Constants;

/**
 * Created by whutwf on 17-3-28.
 */

public class MovieSearchFragment extends MovieBaseFragment {

    private static final String TAG = "MovieSearchFragment";

    public MovieSearchFragment() {
        super();
        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_SEARCH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                Log.i(TAG, "QueryTextSubmit: " + query);
                paramsHashMap.put(Constants.Params.DOUBAN_MOVIE_QUERY, query);

                updateItems();
//                updatePageSettings();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "QueryTextChange: " + newText);
                return true;
            }
        });

    }
}
