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

public class SettingsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        setTitle(" Settings ");
        viewPager =  findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());





        if(MainApplication.message == "Japanese"){
            adapter.addFragment(new LanguageFragment(), "言語");
            adapter.addFragment(new PrinterFragment(), "プリンタ");
            adapter.addFragment(new WebApiFragment(), "ウェブAPI");
            adapter.addFragment(new SMTPFragment(), "SMTP");
            adapter.addFragment(new OthersFragment(), "その他");
            setTitle(" 設定 ");
        }


       else if(MainApplication.message == "Bangla"){
                adapter.addFragment(new LanguageFragment(), "ভাষা");
                adapter.addFragment(new PrinterFragment(), "মুদ্রাকর");
                adapter.addFragment(new WebApiFragment(), "ওয়েবAPI");
                adapter.addFragment(new SMTPFragment(), "SMTP");
                adapter.addFragment(new OthersFragment(), "অন্যান্য");
                setTitle(" সেটিংস ");

        }



      else   if(MainApplication.message == "English"){
                adapter.addFragment(new LanguageFragment(), "Language");
                adapter.addFragment(new PrinterFragment(), "Printer");
                adapter.addFragment(new WebApiFragment(), "WebAPI");
                adapter.addFragment(new SMTPFragment(), "SMTP");
                adapter.addFragment(new OthersFragment(), "Other");
                 setTitle(" Settings ");

        }

        else {

            adapter.addFragment(new LanguageFragment(), "Language");
            adapter.addFragment(new PrinterFragment(), "Printer");
            adapter.addFragment(new WebApiFragment(), "WebAPI");
            adapter.addFragment(new SMTPFragment(), "SMTP");
            adapter.addFragment(new OthersFragment(), "Other");

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
