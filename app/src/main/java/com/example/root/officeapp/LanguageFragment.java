package com.example.root.officeapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.root.officeapp.golobal.MainApplication;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LanguageFragment extends Fragment {

    Spinner spinner;
    TextView textView;


    public LanguageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view. findViewById(R.id.text);
        spinner = view. findViewById(R.id.spinner);

        List<String> list = new ArrayList<>();

        list.add("Select language");
        list.add("English");
        list.add("Japanese");
        list.add("Bangla");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
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
                        startActivity(new Intent(getContext(), MainActivity.class));
                        break;
                    case 2:
                        MainApplication.message = "Japanese";
                        startActivity(new Intent(getContext(), MainActivity.class));
                        break;
                    case 3:
                        MainApplication.message = "Bangla";
                        startActivity(new Intent(getContext(), MainActivity.class));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if (MainApplication.message == "Japanese") {
            textView.setText(getResources().getString(R.string.language_change_japan));
        } else if (MainApplication.message == "Bangla") {
            textView.setText(getResources().getString(R.string.language_change_ban));
        } else if (MainApplication.message == "English") {
            textView.setText(getResources().getString(R.string.language_change_eng));
        }
    }
}
