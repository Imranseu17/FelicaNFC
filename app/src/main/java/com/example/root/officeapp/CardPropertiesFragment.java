package com.example.root.officeapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.root.officeapp.golobal.MainApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardPropertiesFragment extends Fragment {


    Button cardPropeties;


    public CardPropertiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_properties, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardPropeties = view.findViewById(R.id.cardproperties_btn);

        if(MainApplication.message == "Japanese"){
            cardPropeties.setText("カードのプロパティを表示する");

        }

        else if(MainApplication.message == "Bangla"){
            cardPropeties.setText("কার্ড বৈশিষ্ট্য প্রদর্শন করুন");

        }
        else  if(MainApplication.message == "English"){
            cardPropeties.setText("Show the Card Properties");

        }
        cardPropeties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),CardPropertiesActivity.class));
            }
        });
    }
}
