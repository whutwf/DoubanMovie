package com.study.whutwf.doubanmovie.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.study.whutwf.doubanmovie.R;

/**
 * Created by whutwf on 17-3-29.
 */

public abstract  class SingleFragmentActivity extends BaseActivity{

    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_frame);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_frame, fragment)
                    .commit();
        }
    }
}
