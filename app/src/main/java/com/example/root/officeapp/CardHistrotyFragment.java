package com.example.root.officeapp;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.root.officeapp.nfcfelica.HistoryListData;
import com.example.root.officeapp.nfcfelica.HttpResponsAsync;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardHistrotyFragment extends Fragment {

    ListView cardHistoryList;
    ReadCard readCard = new ReadCard();
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    Tag tag;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_card_histroty, container, false);
        cardHistoryList = v.findViewById(R.id.cardHistoryList);

        pendingIntent = PendingIntent.getActivity(
                getContext(), 0, new Intent(getContext(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

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


        mAdapter = NfcAdapter.getDefaultAdapter(getContext().getApplicationContext());


        tag = getActivity().getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);



        readCard.ReadTag(tag);

        readCard.SetReadCardData(tag,readCard.webAPI,readCard.readCardArgument);



        for (int i2 = 0; i2 <readCard.readCardArgument.CardHistory.size(); i2++) {
            HistoryListData dataTemp = new HistoryListData();
            HttpResponsAsync.ReadCardArgumentCardHistory cardHistory = readCard.readCardArgument.CardHistory.get(i2);
            dataTemp.setTime(readCard.getFormatDate(cardHistory.HistoryTime));
            dataTemp.setType(cardHistory.HistoryType);
            readCard.historyListData.add(dataTemp);
        }

        HistoryListAdapter historyListAdapter = new HistoryListAdapter(getContext(),
              readCard.historyListData);

        cardHistoryList.setAdapter(historyListAdapter);


        return  v;

    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(getActivity(), pendingIntent,
                intentFiltersArray, techListsArray);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(getActivity());
    }




}
