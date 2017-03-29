package com.study.whutwf.doubanmovie.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.support.Constants;

/**
 * Created by whutwf on 17-3-28.
 */

public class MovieSearchFragment extends MovieBaseFragment {

    public MovieSearchFragment() {
        super();
        paramsHashMap.put("url", Constants.Urls.DOUBAN_MOVIE_TOP250);
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}
