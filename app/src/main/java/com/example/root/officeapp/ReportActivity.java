package com.example.root.officeapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.root.officeapp.golobal.MainApplication;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setTitle(" Reports ");
        viewPager =  findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
       ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (MainApplication.message == "Japanese") {
            adapter.addFragment(new PosFragment(), "販売時点情報（時間別）");
            adapter.addFragment(new GasFragment(), "時間別ガス販売");
            setTitle(" レポート ");

        } else if (MainApplication.message == "Bangla") {
            adapter.addFragment(new PosFragment(), "বিক্রয় সময় পয়েন্ট দ্বারা বিক্রয়");
            adapter.addFragment(new GasFragment(), "সময় দ্বারা গ্যাস বিক্রয়");
            setTitle(" প্রতিবেদন ");
        } else if (MainApplication.message == "English") {
            adapter.addFragment(new PosFragment(), "Pos Sales By Time");
            adapter.addFragment(new GasFragment(), "Gas Sales By Time");
            setTitle(" Reports ");
        }

        else {
            adapter.addFragment(new PosFragment(), "Pos Sales By Time");
            adapter.addFragment(new GasFragment(), "Gas Sales By Time");

        }
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
