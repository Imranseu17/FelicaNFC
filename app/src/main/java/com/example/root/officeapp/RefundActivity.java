package com.example.root.officeapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RefundActivity extends AppCompatActivity {

    ReadCard readCard = new ReadCard();
    TextView textView;
    PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        setTitle(" Refund ");
        submit = findViewById(R.id.submit);
        textView = findViewById(R.id.refundData);
        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        textView.setText(
                "Account NO:  "+sharedpreferences.getString("customerID","")
                        +"\n"+"Credit:  "+sharedpreferences.getString("credit","")
                        +"\n"+"Refund1:  "+ sharedpreferences.getString("refund1","")
                        +"\n"+"Refund2:  "+sharedpreferences.getString("refund2",""));



        mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());


    }

    @Override
    protected void onResume() {
        super.onResume();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(RefundActivity.this)
                        .setTitle("Card Reader")
                        .setMessage("Please place card on the reader")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(RefundActivity.this)
                                        .setTitle("Card Access")
                                        .setMessage("Please Tab  Card")
                                        .setCancelable(false)
                                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Whatever...
                                            }
                                        }).show();

                                pendingIntent = PendingIntent.getActivity(
                                        RefundActivity.this, 0,
                                        new Intent(RefundActivity.this,
                                                getClass()).
                                                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

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
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .show();


                mAdapter.enableForegroundDispatch(RefundActivity.this,
                        pendingIntent, intentFiltersArray, techListsArray);


            }


        });


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        readCard.ReadTag(tag);
        readCard.SetReadCardData(tag,readCard.webAPI,readCard.readCardArgument);

        boolean data = readCard.SetReadCardData(tag,readCard.webAPI,readCard.readCardArgument);

        if(data){

            if(readCard.readCardArgument.CardGroup.equals("77")){

                new AlertDialog.Builder(RefundActivity.this)
                        .setMessage("Card Reading ........ ")
                        .setCancelable(false)
                        .show();

                new AlertDialog.Builder(RefundActivity.this)
                        .setMessage("Processing the Card Information ........ ")
                        .setCancelable(false)
                        .show();


            }

            else {
                new AlertDialog.Builder(RefundActivity.this)
                        .setMessage("It is not a target customer's card")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                            }
                        }).show();
            }
        }





        }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }

}
