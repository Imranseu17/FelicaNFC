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
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddGasActivity extends AppCompatActivity {

    TextView accontNOtext,addVolume,basePrice,gas,tax,total;
    Spinner s;
    TextInputLayout amountText;
    String amount;

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
        amountText = findViewById(R.id.amountSelect);
        accontNOtext.setText("AccountNo                  "+accountNO);
        s =  findViewById(R.id.spinner);
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




}
