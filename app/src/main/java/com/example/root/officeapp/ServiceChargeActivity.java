package com.example.root.officeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.root.officeapp.golobal.MainApplication;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServiceChargeActivity extends AppCompatActivity {

    Spinner spinner;
    TextView chargeSelect,chargeText,chargeSelectText;
    Button save;
    TextInputLayout serviceCharge;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_charge);

        setTitle(" Service Charge ");
        chargeSelect = findViewById(R.id.chargeSelect);
        chargeText = findViewById(R.id.chargeText);
        chargeSelectText = findViewById(R.id.chargeSelectText);
        spinner = findViewById(R.id.spinner);
        save = findViewById(R.id.save);
        serviceCharge = findViewById(R.id.serviceCharge);
        Calendar calendar = Calendar.getInstance();
        final String currentDate = DateFormat.getDateTimeInstance().format(calendar.getTime());
        final List<String> list = new ArrayList<>();

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
         String slot = sharedpreferences.getString("key","");
         String parcentege = sharedpreferences.getString("parcentege","");

         if(parcentege.isEmpty()){
             chargeSelect.setText(slot);
         }

         else {
             chargeSelect.setText(currentDate + "  " + parcentege +"%"+ "  charge is applicable ");
         }




        list.add("Select Charge");
        list.add("100 - 400 = 5");
        list.add("401 - 1500 = 10");
        list.add("1501 - 3000 = 15");
        list.add("3001 - 5000 = 20");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        chargeSelect.setText(currentDate + " " + list.get(1) + " charge is applicable ");
                        break;
                    case 2:
                        chargeSelect.setText(currentDate + " " + list.get(2) + " charge is applicable ");
                        break;
                    case 3:
                        chargeSelect.setText(currentDate + " " + list.get(3) + " charge is applicable ");
                        break;
                    case 4:
                        chargeSelect.setText(currentDate + " " + list.get(4) + " charge is applicable ");
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if(MainApplication.message == "Japanese"){
            setTitle(" সサービス料 ");
            chargeText.setText(" সサービス料 ");
            chargeSelectText.setText(" 料金を選択してください ");
            save.setText("セーブ");


        }

        else if(MainApplication.message == "Bangla"){

            setTitle(" সেবা খরচ ");
            chargeText.setText(" সেবা খরচ ");
            chargeSelectText.setText(" চার্জ নির্বাচন করুন ");
            save.setText("সংরক্ষণ করুন");

        }

        else if(MainApplication.message == "English"){

            setTitle(" Service charge ");
            chargeText.setText(" Service charge ");
            chargeSelectText.setText(" Please Select the charge ");
            save.setText("Save");

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                editor.putString("key",chargeSelect.getText().toString().trim());
                editor.putString("parcentege",serviceCharge.getEditText().getText().toString().trim());
                editor.apply();
                editor.commit();


            }
        });
    }
}
