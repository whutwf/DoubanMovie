package com.study.whutwf.doubanmovie.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.study.whutwf.doubanmovie.R;

/**
 * Created by whutwf on 17-4-7.
 */

public class MoviePageFragment extends Fragment {
    private static final String ARG_URL = "movie_page_uri";

    private Uri mUri;
    private WebView mWebView;

    public static MoviePageFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_URL, uri);

        MoviePageFragment fragment = new MoviePageFragment();
        fragment.setArguments(args);gt
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_page, container, false);

        mWebView = (WebView) v.findViewById(R.id.fragment_movie_page_web_view);

        return v;
    }
}
