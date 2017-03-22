package com.study.whutwf.doubanmovie.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.study.whutwf.doubanmovie.R;
import com.study.whutwf.doubanmovie.ui.fragment.PartThreeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final int PAGE_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeBlue);
        mLayoutResId = R.layout.activity_main;
        super.onCreate(savedInstanceState);

        initViewPagerAndTabs();
    }

    private void initViewPagerAndTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPager.setOffscreenPageLimit(PAGE_COUNT);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(PartThreeFragment.createInstance(20), getString(R.string.tab_1));
//        pagerAdapter.addFragment(PartThreeFragment.createInstance(4), getString(R.string.tab_2));
//        pagerAdapter.addFragment(PartThreeFragment.createInstance(4), getString(R.string.tab_2));
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

}
