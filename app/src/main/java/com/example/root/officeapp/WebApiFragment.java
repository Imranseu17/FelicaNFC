package com.example.root.officeapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.root.officeapp.golobal.MainApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebApiFragment extends Fragment {

    TextInputLayout domain1,domain2,domain3,apikey,poskey;
    Button save;


    public WebApiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_api, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        domain1 = view.findViewById(R.id.domain1);
        domain2 = view.findViewById(R.id.domain2);
        domain3 = view.findViewById(R.id.domain3);
        apikey = view.findViewById(R.id.apikey);
        poskey = view.findViewById(R.id.poskey);
        save = view.findViewById(R.id.save);

        if(MainApplication.message == "Japanese"){
            domain1.setHint("ドメイン：1");
            domain2.setHint("ドメイン：2");
            domain3.setHint("ドメイン：3");
            apikey.setHint("アプリケーションプログラムインタフェースキー");
            poskey.setHint("販売キーのポイント");
            save.setText("セーブ");

        }

        else if(MainApplication.message == "Bangla"){

            domain1.setHint("ডোমেইন:এক");
            domain2.setHint("ডোমেইন: দুই");
            domain3.setHint("ডোমেইন:তিন");
            apikey.setHint("অ্যাপ্লিকেশন প্রোগ্রাম ইন্টারফেস কী");
            poskey.setHint("বিক্রয় কী পয়েন্ট");
            save.setText("সংরক্ষণ করুন");

        }

        else if(MainApplication.message == "English"){

            domain1.setHint("Domain:1");
            domain2.setHint("Domain:2");
            domain3.setHint("Domain:3");
            apikey.setHint("ApiKey");
            poskey.setHint("PosKey");
            save.setText("Save");

        }
    }
}
