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
import android.widget.TextView;

import com.example.root.officeapp.golobal.MainApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class OthersFragment extends Fragment {

    Switch outputlog;
    TextView levezero;
    TextInputLayout customerWordcount,companyname;
    Button save;


    public OthersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_others, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        outputlog = view.findViewById(R.id.outputlog);
        levezero = view.findViewById(R.id.leavezero);
        customerWordcount = view.findViewById(R.id.wordcount);
        companyname = view.findViewById(R.id.comapanyname);
        save = view.findViewById(R.id.save);

        if(MainApplication.message == "Japanese"){
            outputlog.setText("出力ログ                                                                                   ");
            levezero.setText("顧客IDの先頭にゼロを残します。入力フォームで単語の数を編集できます。");
            customerWordcount.setHint("顧客IDの語数");
            companyname.setHint("領収書/電子メールの会社名");
            save.setText("セーブ");

        }

        else if(MainApplication.message == "Bangla"){

            outputlog.setText("আউটপুট লগ                                                                                 ");
            levezero.setText("গ্রাহক আইডি এর শুরুতে জিরো ছেড়ে দিন। আপনি ইনপুট ফর্ম দিয়ে শব্দগুলির সংখ্যা সম্পাদনা করতে পারেন।");
            customerWordcount.setHint("গ্রাহক আইডি এর শব্দ গণনা");
            companyname.setHint("প্রাপ্তি / ইমেলের জন্য কোম্পানির নাম");
            save.setText("সংরক্ষণ করুন");

        }

        else if(MainApplication.message == "English"){

            outputlog.setText("Output Log                                                                                ");
            levezero.setText("Leave Zero at the beginning of the customer ID.\n" +
                    " You can edit the number of words with the input form.");
            customerWordcount.setHint("Word Count of Customer ID");
            companyname.setHint("Company Name for receipt/email");
            save.setText("Save");

        }
    }
}
