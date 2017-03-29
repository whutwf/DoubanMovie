package com.study.whutwf.doubanmovie.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.ui.fragment.MovieCommingSoonFragment;
import com.study.whutwf.doubanmovie.ui.fragment.MovieInTheatersFragment;
import com.study.whutwf.doubanmovie.ui.fragment.MovieTopFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final int PAGE_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        mLayoutResId = R.layout.activity_main;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView (){
        super.initView();
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPager.setOffscreenPageLimit(PAGE_COUNT);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(MovieTopFragment.newInstance(), getString(R.string.top250));
        pagerAdapter.addFragment(MovieInTheatersFragment.newInstance(), getString(R.string.in_theaters));
        pagerAdapter.addFragment(MovieCommingSoonFragment.newInstance(), getString(R.string.coming_soon));
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_pager_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_go_to_search:
                return prepareIntent(SearchActivity.class);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private boolean prepareIntent(Class clazz) {
        startActivity(new Intent(MainActivity.this, clazz));
        return true;
    }
}
