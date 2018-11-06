package com.example.root.officeapp;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
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
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    ReadCard readCard = new ReadCard();
    Tag tag;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_properties, container, false);

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


        mAdapter = NfcAdapter.getDefaultAdapter(getContext().getApplicationContext());

        try {

            Bundle bundle = this.getArguments();
            if(bundle != null){
                tag = bundle.getParcelable("tag");
            }

            //tag = getActivity().getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);

        }catch (Exception e){
            e.printStackTrace();
        }






        readCard.ReadTag(tag);

        boolean data =  readCard.SetReadCardData(tag,readCard.webAPI,readCard.readCardArgument);

//        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
//
//        cardPropeties.setText(
//                "Version NO:"+sharedpreferences.getString("versionNO","")+"\n"+
//                "Card Status: "+  sharedpreferences.getString("cardStatus","") +"\n"
//                +"Card ID: "+ sharedpreferences.getString("cardID","")
//                +"\n"+"Customer ID: "+sharedpreferences.getString("customerID","")
//                +"\n"+"Card Group: "+ sharedpreferences.getString("cardGroup","")
//                +"\n"+"Credit: "+sharedpreferences.getString("credit","")
//                +"\n"+"Unit: "+sharedpreferences.getString("unit","")
//                +"\n"+"Basic Fee: "+sharedpreferences.getString("basicFee","")
//                +"\n"+"Refund1: "+sharedpreferences.getString("refund1","")
//                +"\n"+"Refund2: "+sharedpreferences.getString("refund2","")
//                +"\n"+"Untreated Fee: "+sharedpreferences.getString("untreatedFee","")
//                +"\n"+"Card History NO: "+sharedpreferences.getString("historyNo","")
//                +"\n"+"Card Error NO: "+sharedpreferences.getString("errorNo","")
//                +"\n"+"Open Count: "+sharedpreferences.getString("openCount","")
//                +"\n"+"Lid Time: "+sharedpreferences.getString("lidTime","")
//        );

        if(data){

            cardPropeties.setText(
                    "Version NO:"+readCard.readCardArgument.VersionNo+"\n"+
                    "Card Status: "+ readCard .readCardArgument.CardStatus +"\n"
                    +"Card ID: "+ readCard .readCardArgument.CardIdm
                    +"\n"+"Customer ID: "+readCard .readCardArgument.CustomerId
                    +"\n"+"Card Group: "+ readCard. readCardArgument.CardGroup
                    +"\n"+"Credit: "+readCard. readCardArgument.Credit
                    +"\n"+"Unit: "+readCard. readCardArgument.Unit
                    +"\n"+"Basic Fee: "+readCard. readCardArgument.BasicFee
                    +"\n"+"Refund1: "+readCard. readCardArgument.Refund1
                    +"\n"+"Refund2: "+readCard. readCardArgument.Refund2
                    +"\n"+"Untreated Fee: "+readCard. readCardArgument.UntreatedFee
                    +"\n"+"Card History NO: "+readCard. readCardArgument.CardHistoryNo
                    +"\n"+"Card Error NO: "+readCard. readCardArgument.ErrorNo
                    +"\n"+"Open Count: "+readCard. readCardArgument.OpenCount
                    +"\n"+"Lid Time: "+readCard. readCardArgument.LidTime
            );
        }



        return view;


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
