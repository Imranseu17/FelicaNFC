package com.example.root.officeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddGasActivity extends AppCompatActivity {

    TextView accontNOtext,addVolume,basePrice,gas,tax,total;
    Spinner spinner;
    TextInputLayout amountText;
    String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gas);
        setTitle(" Add Gas ");

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);

        String accountNO = sharedpreferences.getString("customerID","");
        accontNOtext = findViewById(R.id.account);
        addVolume = findViewById(R.id.addVolume);
        basePrice = findViewById(R.id.basePrice);
        gas = findViewById(R.id.gas);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);
        amountText = findViewById(R.id.amountSelect);
        accontNOtext.setText("AccountNo                "+accountNO);
        spinner = findViewById(R.id.spinner);
        final List<String> list = new ArrayList<>();
        list.add("Manual");
        list.add("500");
        list.add("1000");
        list.add("1500");
        list.add("2000");
        list.add("2500");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddGasActivity.this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        amount =  list.get(0);
                        break;
                    case 1:
                        amount =  list.get(1);
                        break;
                    case 2:
                        amount =  list.get(2);
                        break;
                    case 3:
                        amount =  list.get(3);
                        break;
                    case 4:
                        amount =  list.get(4);
                        break;

                    case 5:
                        amount =  list.get(5);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        amountText.getEditText().setHint(amount);
        DecimalFormat df2 = new DecimalFormat(".##");
        double value = Double.parseDouble(amount)/9.1;
        addVolume.setText(" Add Volume(m3) "+Double.parseDouble(df2.format(value)));
        basePrice.setText("Base Price  "+" Amount  "+"  1  "+" Price  "+" 0.00");
        gas.setText("Gas  "+" Amount "+value + " Price "+amount+"TK");
        tax.setText(" Tax   "+" 0.00"+"TK");
        total.setText(" Total  "+amount+"TK");


        }


}
