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

public class InspectCardActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_card);
        setTitle(" Inspect Card ");
        viewPager =  findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (MainApplication.message == "Japanese") {
            adapter.addFragment(new CardPropertiesFragment(), "カードのプロパティ");
            adapter.addFragment(new CardHistrotyFragment(), "カード履歴");
            adapter.addFragment(new ErrorHistoryFragment(), "エラー履歴");
            setTitle(R.string.card_japan);
        } else if (MainApplication.message == "Bangla") {
            adapter.addFragment(new CardPropertiesFragment(), "কার্ড বৈশিষ্ট্য");
            adapter.addFragment(new CardHistrotyFragment(), "কার্ড ইতিহাস");
            adapter.addFragment(new ErrorHistoryFragment(), "ত্রুটি ইতিহাস");
            setTitle(R.string.card_ban);
        } else if (MainApplication.message == "English") {
            adapter.addFragment(new CardPropertiesFragment(), "Card Properties");
            adapter.addFragment(new CardHistrotyFragment(), "Card History");
            adapter.addFragment(new ErrorHistoryFragment(), "Error History");
            setTitle(R.string.card_eng);
        }

        else {
            adapter.addFragment(new CardPropertiesFragment(), "Card Properties");
            adapter.addFragment(new CardHistrotyFragment(), "Card History");
            adapter.addFragment(new ErrorHistoryFragment(), "Error History");
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
