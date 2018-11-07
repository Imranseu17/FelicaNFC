package com.example.root.officeapp;


import android.app.Activity;
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
import android.widget.Toast;

import com.example.root.officeapp.golobal.MainApplication;

import static com.example.root.officeapp.ReadCard.choice;


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


        return inflater.inflate(R.layout.fragment_card_properties, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardPropeties = view.findViewById(R.id.properties);

        pendingIntent = PendingIntent.getActivity(
                getContext(), 0, new Intent(getContext(),
                        getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

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







    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Bundle bundle = activity.getIntent().getExtras();

        if(bundle != null){

            String s = bundle.getString("test");
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            tag = bundle.getParcelable("tag");
        }



        readCard.ReadTag(tag);

        boolean data =  readCard.SetReadCardData(tag,readCard.webAPI,readCard.readCardArgument);



        if(data) {

            cardPropeties.setText(
                    "Version NO:" + readCard.readCardArgument.VersionNo + "\n" +
                            "Card Status: " + readCard.readCardArgument.CardStatus + "\n"
                            + "Card ID: " + readCard.readCardArgument.CardIdm
                            + "\n" + "Customer ID: " + readCard.readCardArgument.CustomerId
                            + "\n" + "Card Group: " + readCard.readCardArgument.CardGroup
                            + "\n" + "Credit: " + readCard.readCardArgument.Credit
                            + "\n" + "Unit: " + readCard.readCardArgument.Unit
                            + "\n" + "Basic Fee: " + readCard.readCardArgument.BasicFee
                            + "\n" + "Refund1: " + readCard.readCardArgument.Refund1
                            + "\n" + "Refund2: " + readCard.readCardArgument.Refund2
                            + "\n" + "Untreated Fee: " + readCard.readCardArgument.UntreatedFee
                            + "\n" + "Card History NO: " + readCard.readCardArgument.CardHistoryNo
                            + "\n" + "Card Error NO: " + readCard.readCardArgument.ErrorNo
                            + "\n" + "Open Count: " + readCard.readCardArgument.OpenCount
                            + "\n" + "Lid Time: " + readCard.readCardArgument.LidTime
            );


        }


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
