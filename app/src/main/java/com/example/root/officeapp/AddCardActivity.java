package com.example.root.officeapp;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddCardActivity extends AppCompatActivity {

    Button button;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    ReadCard readCard = new ReadCard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        button = findViewById(R.id.submit);
        setTitle(" ADD CARD ");

        pendingIntent = PendingIntent.getActivity(
                AddCardActivity.this, 0, new Intent(
                        AddCardActivity.this, getClass()).
                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                0);

        mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());


    }

    @Override
    protected void onResume() {
        super.onResume();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(AddCardActivity.this)
                        .setTitle("Accept Addition?")
                        .setMessage("Are you sure you want to issue a card to this contact?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(AddCardActivity.this)
                                        .setTitle("Card Reader")
                                        .setMessage("Please place card on the reader")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new AlertDialog.Builder(AddCardActivity.this)
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
                                                        AddCardActivity.this, 0, new Intent(
                                                                AddCardActivity.this, getClass()).
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
                                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                        .show();
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .show();




                mAdapter.enableForegroundDispatch(AddCardActivity.this,
                        pendingIntent, intentFiltersArray, techListsArray);

            }
        });








    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        readCard.ReadTag(tag);

        boolean data = readCard.SetReadCardData(tag,readCard.webAPI,readCard.readCardArgument);

        if(data){

            if(readCard.readCardArgument.CardGroup.equals("77")){



                new AlertDialog.Builder(AddCardActivity.this)
                        .setMessage("Card Reading ........ ")
                        .setCancelable(false)
                        .show();

                new AlertDialog.Builder(AddCardActivity.this)
                        .setMessage("Processing the Card Information ........ ")
                        .setCancelable(false)
                        .show();

                startActivity(new Intent(this,ReadCard.class));
            }

            else {
                new AlertDialog.Builder(AddCardActivity.this)
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
