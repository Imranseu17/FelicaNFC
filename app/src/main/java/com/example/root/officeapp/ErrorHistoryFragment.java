package com.example.root.officeapp;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.officeapp.golobal.MainApplication;
import com.example.root.officeapp.nfcfelica.ErrorListData;
import com.example.root.officeapp.nfcfelica.HttpResponsAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ErrorHistoryFragment extends Fragment {

    ListView cardErrorList;
    ReadCard readCard = new ReadCard();
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    Tag tag;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       View view =   inflater.inflate(R.layout.fragment_error_history, container, false);

       cardErrorList = view.findViewById(R.id.cardErrorList);

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

//        tag = getActivity().getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
//
//
//
//        readCard.ReadTag(tag);
//
//        readCard.SetReadCardData(tag,readCard.webAPI,readCard.readCardArgument);
//
//
//        for (int i2 = 0; i2 < readCard. readCardArgument.ErrorHistory.size(); i2++) {
//            ErrorListData dataTemp = new ErrorListData();
//            HttpResponsAsync.ReadCardArgumentErrorHistory cardErrorHistory = readCard. readCardArgument.ErrorHistory.get(i2);
//            dataTemp.setGroup(cardErrorHistory.ErrorGroup);
//            dataTemp.setTime(readCard. getFormatDate(cardErrorHistory.ErrorTime));
//            dataTemp.setType(cardErrorHistory.ErrorType);
//            readCard. errorListData.add(dataTemp);
//        }
//
//        CardErrorListAdapter cardErrorListAdapter = new CardErrorListAdapter(
//                getContext(),readCard. errorListData
//        );
//
//        cardErrorList.setAdapter(cardErrorListAdapter);

        return  view;
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
