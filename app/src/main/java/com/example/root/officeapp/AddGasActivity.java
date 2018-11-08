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
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddGasActivity extends AppCompatActivity {

    TextView accontNOtext,addVolume,basePrice,gas,tax,total;
    Spinner s;
    TextInputLayout amountText;
    String amount;
    Button submit;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    ReadCard readCard = new ReadCard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gas);
        setTitle(" ADD Gas ");

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        String accountNO = sharedpreferences.getString("customerID","");
        accontNOtext = findViewById(R.id.account);
        addVolume = findViewById(R.id.addVolume);
        basePrice = findViewById(R.id.basePrice);
        gas = findViewById(R.id.gas);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);
        submit = findViewById(R.id.submit);
        amountText = findViewById(R.id.amountSelect);
        s =  findViewById(R.id.spinner);



        mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        accontNOtext.setText("AccountNo                  "+accountNO);

        final List<String> list = new ArrayList<>();
        list.add("500");
        list.add("1000");
        list.add("1500");
        list.add("2000");
        list.add("2500");
        list.add("Manual");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                amount = (String) parent.getItemAtPosition(position);
                amountText.getEditText().setHint(amount);
                DecimalFormat df2 = new DecimalFormat(".##");
                double value = Double.parseDouble(amount)/9.1;
                addVolume.setText(" Add Volume(m3)       "+Double.parseDouble(df2.format(value)));
                basePrice.setText("Base Price  "+"    Amount  "+"  1  "+" Price  "+" 0.00TK");
                gas.setText("Gas  "+"    Amount    "+Double.parseDouble(df2.format(value))+
                        "   Price    "+amount+"TK");
                tax.setText("     Tax   "+" 0.00"+"TK");
                total.setText("     Total  "+amount+"TK");



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });









        }

    @Override
    protected void onResume() {
        super.onResume();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AddGasActivity.this)
                        .setTitle("Add Gas")
                        .setMessage("Are you sure want to add these funds?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(AddGasActivity.this)
                                        .setTitle("Card Reader")
                                        .setMessage("Please place card on the reader")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                new AlertDialog.Builder(AddGasActivity.this)
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
                                                        AddGasActivity.this, 0, new Intent(
                                                                AddGasActivity.this, getClass()).
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


                mAdapter.enableForegroundDispatch(AddGasActivity.this,
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

                new AlertDialog.Builder(AddGasActivity.this)
                        .setMessage("Card Reading ........ ")
                        .setCancelable(false)
                        .show();

                new AlertDialog.Builder(AddGasActivity.this)
                        .setMessage("Processing the Card Information ........ ")
                        .setCancelable(false)
                        .show();


            }

            else {
                new AlertDialog.Builder(AddGasActivity.this)
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
