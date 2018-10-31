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
import android.widget.Button;
import android.widget.TextView;

import com.example.root.officeapp.golobal.MainApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardPropertiesFragment extends Fragment {


   TextView cardPropeties;
   ReadCard readCard = new ReadCard();
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;


    public CardPropertiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_properties, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardPropeties = view.findViewById(R.id.properties);
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


        mAdapter = NfcAdapter.getDefaultAdapter(getActivity().getApplicationContext());






    }

    @Override
    public void onStart() {
        super.onStart();

        Tag tag  = getActivity().getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            return;
        }

        readCard.ReadTag(tag);
        boolean data = readCard.SetReadCardData(tag,readCard.webAPI,readCard.readCardArgument);

        if(data){

            cardPropeties.setText(
                    "Version NO:"+readCard .readCardArgument.VersionNo+"\n"+
                            "Card Status: "+  Integer.toHexString(Integer.parseInt(readCard . readCardArgument.CardStatus)) +"\n"
                            +"Card ID: "+readCard . readCardArgument.CardIdm
                            +"\n"+"Customer ID: "+readCard . readCardArgument.CustomerId
                            +"\n"+"Card Group: "+ Integer.toHexString(Integer.parseInt(readCard . readCardArgument.CardGroup))
                            +"\n"+"Credit: "+readCard . readCardArgument.Credit
                            +"\n"+"Unit: "+readCard . readCardArgument.Unit
                            +"\n"+"Basic Fee: "+readCard . readCardArgument.BasicFee
                            +"\n"+"Refund1: "+readCard . readCardArgument.Refund1
                            +"\n"+"Refund2: "+readCard . readCardArgument.Refund2
                            +"\n"+"Untreated Fee: "+readCard . readCardArgument.UntreatedFee
                            +"\n"+"Card History NO: "+readCard . readCardArgument.CardHistoryNo
                            +"\n"+"Card Error NO: "+readCard . readCardArgument.ErrorNo
                            +"\n"+"Open Count: "+readCard . readCardArgument.OpenCount
                            +"\n"+"Lid Time: "+readCard . readCardArgument.LidTime
            );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(getActivity(), pendingIntent, intentFiltersArray, techListsArray);
    }



    @Override
    public void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(getActivity());
    }
}
