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
import com.study.whutwf.doubanmovie.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by whutwf on 17-3-14.
 */

public class PartThreeFragment extends Fragment {

    private static final String ITEMS_COUNT_KEY = "PartThreeFragment$ItemCount";

    public static PartThreeFragment createInstance(int itemsCount) {
        PartThreeFragment partThreeFragent = new PartThreeFragment();
        //Fragment中使用Bundle传送数据
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        partThreeFragent.setArguments(bundle);
        return partThreeFragent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_movie_list, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    public void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(createItemList());
        recyclerView.setAdapter(recyclerAdapter);
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        Bundle bundle = getArguments();

        if (bundle != null) {
            int itemsCount = bundle.getInt(ITEMS_COUNT_KEY);
            for (int i = 0; i < itemsCount; ++i) {
                itemList.add("Item" + i);
            }
        }
        return itemList;
    }
}
