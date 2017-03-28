package com.study.whutwf.doubanmovie.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.whutwf.doubanmovie.R;

/**
 * Created by whutwf on 17-3-28.
 */

public class SearchMovieFragment extends Fragment {

    public static SearchMovieFragment newInstance() {
        return new SearchMovieFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_serch, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.fragment_movie_serch_recycler_view);

        //这句话先摆在这
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }
}
