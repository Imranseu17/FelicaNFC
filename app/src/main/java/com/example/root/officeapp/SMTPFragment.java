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
import android.widget.Switch;

import com.example.root.officeapp.golobal.MainApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class SMTPFragment extends Fragment {


    TextInputLayout automethod,mailaddress,username,password,timeout;
    Switch emailorsms,addcharge,addgas,refundgas,addcard,returncad,activecard,cardlost;
    Button save;


    public SMTPFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_smt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        automethod = view.findViewById(R.id.autoMethod);
        mailaddress = view.findViewById(R.id.mailAddress);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        timeout = view.findViewById(R.id.timeout);
        emailorsms = view.findViewById(R.id.emailorsmsswitch);
        addcharge = view.findViewById(R.id.addcharge);
        addgas = view.findViewById(R.id.addgas);
        refundgas = view.findViewById(R.id.refund);
        addcard = view.findViewById(R.id.addcard);
        activecard = view.findViewById(R.id.activecard);
        cardlost = view.findViewById(R.id.cardlost);
        save = view.findViewById(R.id.save);
        returncad = view.findViewById(R.id.returncard);

        if(MainApplication.message == "Japanese"){
            automethod.setHint("自動メソッド");
            mailaddress.setHint("メールアドレス");
            username.setHint("ユーザー名");
            password.setHint("パスワード");
            timeout.setHint("タイムアウト");
            emailorsms.setText("メール/SMS                                                                               ");
            addcharge.setText("充電を追加する                                                                             ");
            addgas.setText("ガスを加える                                                                                  ");
            refundgas.setText("払い戻しガス                                                                               ");
            addcard.setText("カードを追加                                                                                 ");
            activecard.setText("アクティブカード                                                                           ");
            cardlost.setText("カードが失われた                                                                             ");
            save.setText("セーブ");
            returncad.setText("リターンカード                                                                              ");
        }

       else if(MainApplication.message == "Bangla"){
            automethod.setHint("অটো পদ্ধতি");
            mailaddress.setHint("ইমেইলর ঠিকানা");
            username.setHint("ব্যবহারকারীর নাম");
            password.setHint("পাসওয়ার্ড");
            timeout.setHint("সময় শেষ");
            emailorsms.setText("ইমেইল / এসএমএস                                                                           ");
            addcharge.setText("চার্জ যোগ করুন                                                                               ");
            addgas.setText("গ্যাস যোগ করুন                                                                                  ");
            refundgas.setText("ফেরত গ্যাস                                                                                  ");
            addcard.setText("কার্ড যোগ করুন                                                                                 ");
            activecard.setText("সক্রিয় কার্ড                                                                                 ");
            cardlost.setText("কার্ড হারিয়ে গেছে                                                                               ");
            save.setText("সংরক্ষণ করুন");
            returncad.setText("ফিরে কার্ড                                                                                   ");
        }

       else if(MainApplication.message == "English"){
            automethod.setHint("Auto Method");
            mailaddress.setHint("Mail Address");
            username.setHint("User Name");
            password.setHint("Password");
            timeout.setHint("TimeOut");
            emailorsms.setText("Email/Sms                                                                                ");
            addcharge.setText("Add Charge (OFF/ON)                                                                       ");
            addgas.setText("Add Gas (OFF/ON)                                                                             ");
            refundgas.setText("Refund Gas (OFF/ON)                                                                       ");
            addcard.setText("Add Card (OFF/ON)                                                                           ");
            activecard.setText("Active Card (OFF/ON)                                                                     ");
            cardlost.setText("Card Lost (OFF/ON)                                                                         ");
            save.setText("Save");
            returncad.setText("Return Card (OFF/ON)                                                                      ");
        }

    }
}
