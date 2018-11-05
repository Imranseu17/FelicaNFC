package com.example.root.officeapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.officeapp.golobal.MainApplication;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GasReachargeActivity extends AppCompatActivity {

    Spinner spinner;
    String rechageValue;
    ReadCard card = new ReadCard();
    Tag tag;
    TextView selectCharge,tabCard;

    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_reacharge);
        setTitle(getResources().getString(R.string.gasrecharge_eng));
        spinner = findViewById(R.id.spinner);
        selectCharge = findViewById(R.id.chargeSelectText);
        tabCard = findViewById(R.id.text);
        final List<String> list = new ArrayList<>();
        list.add("Select Charge");
        list.add("100");
        list.add("200");
        list.add("500");
        list.add("1000");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        rechageValue =  list.get(0);
                        break;
                    case 1:
                       rechageValue =  list.get(1);
                        break;
                    case 2:
                       rechageValue =  list.get(2);
                        break;
                    case 3:
                       rechageValue =  list.get(3);
                        break;
                    case 4:
                       rechageValue =  list.get(4);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

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

        if (MainApplication.message == "Japanese") {
            setTitle(getResources().getString(R.string.gasrecharge_japan));
            selectCharge.setText(getResources().getString(R.string.selectCharge_japan));
            tabCard.setText(getResources().getString(R.string.afterselectCharge_japan));
        } else if (MainApplication.message == "Bangla") {
            setTitle(getResources().getString(R.string.gasrecharge_ban));
            tabCard.setText(getResources().getString(R.string.afterselectCharge_ban));
            selectCharge.setText(getResources().getString(R.string.selectCharge_ban));
        } else if (MainApplication.message == "English") {
            setTitle(getResources().getString(R.string.gasrecharge_eng));
            tabCard.setText(getResources().getString(R.string.afterselectCharge_eng));
            selectCharge.setText(getResources().getString(R.string.selectCharge_eng));
        }




    }

    @Override
    protected void onResume() {
        super.onResume();

        mAdapter.enableForegroundDispatch(this, pendingIntent,
                intentFiltersArray, techListsArray);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
         DecimalFormat df2 = new DecimalFormat(".##");
         double value = Double.parseDouble(rechageValue)/9.1;

        if (tag == null) {
            return;
        }
        card.ReadTag(tag);
       boolean response =  card.GasChargeCard(tag, Double.parseDouble(df2.format(value)),0,0,9,"10003419");

        if (response){
            card.WriteStatus(tag,card.historyNO+1);
            Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
        }

        else {
            Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }


}
