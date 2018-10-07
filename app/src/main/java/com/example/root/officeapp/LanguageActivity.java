package com.example.root.officeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.root.officeapp.golobal.MainApplication;

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends AppCompatActivity {

    Spinner spinner;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        textView = findViewById(R.id.text);
        spinner = findViewById(R.id.spinner);

        List<String> list = new ArrayList<>();

        list.add("Select language");
        list.add("English");
        list.add("Japanese");
        list.add("Bangla");

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
                        MainApplication.message = "English";
                        startActivity(new Intent(LanguageActivity.this, MainActivity.class));
                        break;
                    case 2:
                        MainApplication.message = "Japanese";
                        startActivity(new Intent(LanguageActivity.this, MainActivity.class));
                        break;
                    case 3:
                        MainApplication.message = "Bangla";
                        startActivity(new Intent(LanguageActivity.this, MainActivity.class));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if (MainApplication.message == "Japanese") {
            textView.setText(getResources().getString(R.string.language_japan));
        } else if (MainApplication.message == "Bangla") {
            textView.setText(getResources().getString(R.string.language_ban));
        } else if (MainApplication.message == "English") {
            textView.setText(getResources().getString(R.string.language_eng));
        }
    }


}