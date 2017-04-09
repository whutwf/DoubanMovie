package com.study.whutwf.doubanmovie.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.study.whutwf.doubanmovie.R;

/**
 * Created by whutwf on 17-4-7.
 */

public class MoviePageFragment extends Fragment {
    private static final String ARG_URL = "movie_page_uri";

    private Uri mUri;
    private WebView mWebView;
    private ProgressBar mProgressBar;

    //使用Bundle与Activity传递参数
    //在Activity中调用
    public static MoviePageFragment newInstance(Uri uri) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_URL, uri);

        MoviePageFragment fragment = new MoviePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUri = getArguments().getParcelable(ARG_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_page, container, false);

        mProgressBar = (ProgressBar) v.findViewById(R.id.fragment_movie_page_progress_bar);
        mProgressBar.setMax(100);   //WebClient reports in range 0-100

        mWebView = (WebView) v.findViewById(R.id.fragment_movie_page_web_view);
        //启用javascript
        mWebView.getSettings().setJavaScriptEnabled(true);

        //改变浏览器装饰事件
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    Log.i("progressBar", "progress: " + newProgress);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setSubtitle(title);
            }
        });
        //WebViewClient响应渲染事件
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回false，交由WebView处理
                //返回true，交由自己处理
                return false;
            }
        });

        mWebView.loadUrl(mUri.toString());

        return v;
    }
}
