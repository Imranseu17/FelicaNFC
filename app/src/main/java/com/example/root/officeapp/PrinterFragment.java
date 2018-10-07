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
import android.widget.TextView;

import com.example.root.officeapp.golobal.MainApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrinterFragment extends Fragment {

    TextInputLayout operatorname,customername;
    TextView  printername,deviceid;
    Button finddevice,save;


    public PrinterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_printer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        operatorname = view.findViewById(R.id.operator);
        customername = view.findViewById(R.id.customer);
        printername = view.findViewById(R.id.printer);
        deviceid = view.findViewById(R.id.device);
        finddevice = view.findViewById(R.id.find);
        save = view.findViewById(R.id.save);

        if(MainApplication.message == "Japanese"){
            operatorname.setHint("オペレータ名");
            customername.setHint("顧客名");
            printername.setText("プリンタ名");
            deviceid.setText("デバイスID");
            finddevice.setText("デバイスの検索");
            save.setText("セーブ");
        }

       else if(MainApplication.message == "Bangla"){
            operatorname.setHint("অপারেটর নাম");
            customername.setHint("ক্রেতার নাম");
            printername.setText("প্রিন্টার নাম");
            deviceid.setText("ডিভাইস আইডি");
            finddevice.setText("ডিভাইস খুঁজুন");
            save.setText("সংরক্ষণ করুন");
        }

      else   if(MainApplication.message == "English"){
            operatorname.setHint("Operator Name");
            customername.setHint("Customer Name");
            printername.setText("printer Name");
            deviceid.setText("Device ID");
            finddevice.setText("Find Device ");
            save.setText("Save");
        }


    }
}
