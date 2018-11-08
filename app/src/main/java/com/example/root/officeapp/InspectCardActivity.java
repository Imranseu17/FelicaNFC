package com.example.root.officeapp;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.root.officeapp.golobal.MainApplication;
import com.example.root.officeapp.nfcfelica.ErrorListData;
import com.example.root.officeapp.nfcfelica.HistoryListData;
import com.example.root.officeapp.nfcfelica.HttpResponsAsync;

import java.util.ArrayList;
import java.util.List;

public class InspectCardActivity extends AppCompatActivity {


//    private TabLayout tabLayout;
//    private ViewPager viewPager;

    ListView cardProperties, cardHistoryListView,cardErrorList;
    TextView textCardProperties,textcardHistoryListView,textcardErrorList;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    ReadCard readCard = new ReadCard();
    ArrayList<String> propetiesListData = new ArrayList<>();
    ArrayList<HistoryListData> historyListData = new ArrayList();
    ArrayList<ErrorListData> errorListData = new ArrayList();
    AlertDialog optionDialog1,optionDialog2;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_card);
        setTitle(" Inspect Card ");
//        viewPager =  findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//
//        tabLayout = findViewById(R.id.sliding_tabs);
//        tabLayout.setupWithViewPager(viewPager);

        cardProperties = findViewById(R.id.cardProperties);
        cardHistoryListView = findViewById(R.id.cardHistoryList);
        cardErrorList = findViewById(R.id.cardErrorList);
        textCardProperties = findViewById(R.id.textCardProperties);
        textcardHistoryListView = findViewById(R.id.textCardHistory);
        textcardErrorList = findViewById(R.id.textCardError);
        textCardProperties.setVisibility(View.GONE);
        textcardHistoryListView.setVisibility(View.GONE);
        textcardErrorList.setVisibility(View.GONE);
        cardProperties.setVisibility(View.GONE);
        cardHistoryListView.setVisibility(View.GONE);
        cardErrorList.setVisibility(View.GONE);

        pendingIntent = PendingIntent.getActivity(
                InspectCardActivity.this, 0, new Intent(
                        InspectCardActivity.this, getClass()).
                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            ndef.addDataType("text/plain");
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] {ndef};


        techListsArray = new String[][] {
                new String[] { NfcF.class.getName() }
        };



        mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());






    }

//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        if (MainApplication.message == "Japanese") {
//            adapter.addFragment(new CardPropertiesFragment(), "カードのプロパティ");
//            adapter.addFragment(new CardHistrotyFragment(), "カード履歴");
//            adapter.addFragment(new ErrorHistoryFragment(), "エラー履歴");
//            setTitle(R.string.card_japan);
//        } else if (MainApplication.message == "Bangla") {
//            adapter.addFragment(new CardPropertiesFragment(), "কার্ড বৈশিষ্ট্য");
//            adapter.addFragment(new CardHistrotyFragment(), "কার্ড ইতিহাস");
//            adapter.addFragment(new ErrorHistoryFragment(), "ত্রুটি ইতিহাস");
//            setTitle(R.string.card_ban);
//        } else if (MainApplication.message == "English") {
//            adapter.addFragment(new CardPropertiesFragment(), "Card Properties");
//            adapter.addFragment(new CardHistrotyFragment(), "Card History");
//            adapter.addFragment(new ErrorHistoryFragment(), "Error History");
//            setTitle(R.string.card_eng);
//        }
//
//        else {
//            adapter.addFragment(new CardPropertiesFragment(), "Card Properties");
//            adapter.addFragment(new CardHistrotyFragment(), "Card History");
//            adapter.addFragment(new ErrorHistoryFragment(), "Error History");
//        }
//
//        viewPager.setAdapter(adapter);
//    }
//
//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(InspectCardActivity.this,
                pendingIntent, intentFiltersArray, techListsArray);






    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        readCard.ReadTag(tag);
        boolean data = readCard.SetReadCardData(tag,readCard.webAPI,readCard.readCardArgument);
        if(data){

            textCardProperties.setVisibility(View.VISIBLE);
            textcardHistoryListView.setVisibility(View.VISIBLE);
            textcardErrorList.setVisibility(View.VISIBLE);
            cardProperties.setVisibility(View.VISIBLE);
            cardHistoryListView.setVisibility(View.VISIBLE);
            cardErrorList.setVisibility(View.VISIBLE);

            propetiesListData.add("Version NO:          "+readCard. readCardArgument.VersionNo);
            propetiesListData.add("Card Status:         "+ readCard. readCardArgument.CardStatus);
            propetiesListData.add("Card ID:             "+ readCard. readCardArgument.CardIdm);
            propetiesListData.add("Customer ID:         "+readCard. readCardArgument.CustomerId);
            propetiesListData.add("Card Group:          "+ readCard. readCardArgument.CardGroup);
            propetiesListData.add("Credit:              "+readCard . readCardArgument.Credit);
            propetiesListData.add("Unit:                "+ readCard. readCardArgument.Unit);
            propetiesListData.add("Basic Fee:           "+readCard. readCardArgument.BasicFee);
            propetiesListData.add("Refund1:             "+readCard . readCardArgument.Refund1);
            propetiesListData.add("Refund2:             "+readCard . readCardArgument.Refund2);
            propetiesListData.add("Untreated Fee:       "+readCard . readCardArgument.UntreatedFee);
            propetiesListData.add("Open Count:          "+readCard . readCardArgument.OpenCount);
            propetiesListData.add("Card History NO:     "+readCard . readCardArgument.CardHistoryNo);
            propetiesListData.add("Card Error NO:       "+readCard . readCardArgument.ErrorNo);
            propetiesListData.add("Lid Time:            "+readCard . readCardArgument.LidTime);

        }

        ArrayAdapter<String> adapter =   new ArrayAdapter<>(InspectCardActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,propetiesListData);

        cardProperties.setAdapter(adapter);

        for (int i2 = 0; i2 < readCard. readCardArgument.CardHistory.size(); i2++) {
            HistoryListData dataTemp = new HistoryListData();
            HttpResponsAsync.ReadCardArgumentCardHistory cardHistory = readCard. readCardArgument.CardHistory.get(i2);
            dataTemp.setTime(readCard. getFormatDate(cardHistory.HistoryTime));
            dataTemp.setType(cardHistory.HistoryType);
            historyListData.add(dataTemp);
        }

        for ( int i2 = 0; i2 < readCard. readCardArgument.ErrorHistory.size(); i2++) {
            ErrorListData dataTemp = new ErrorListData();
            HttpResponsAsync.ReadCardArgumentErrorHistory cardErrorHistory = readCard. readCardArgument.ErrorHistory.get(i2);
            dataTemp.setGroup(cardErrorHistory.ErrorGroup);
            dataTemp.setTime(readCard. getFormatDate(cardErrorHistory.ErrorTime));
            dataTemp.setType(cardErrorHistory.ErrorType);
            errorListData.add(dataTemp);
        }

        HistoryListAdapter historyListAdapter = new HistoryListAdapter(this,
                historyListData);

        cardHistoryListView.setAdapter(historyListAdapter);


        CardErrorListAdapter cardErrorListAdapter = new CardErrorListAdapter(
                this,errorListData
        );

        cardErrorList.setAdapter(cardErrorListAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }


}
