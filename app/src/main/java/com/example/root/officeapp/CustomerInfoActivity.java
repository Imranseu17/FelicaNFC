package com.example.root.officeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CustomerInfoActivity extends AppCompatActivity {

    String cusName, cusID, cusAccountNo, cusCardNo,
            balence, ebalence, card_status, card_group,
            version_name, credit, unit, refund;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        textView = findViewById(R.id.text);
        Bundle bundle = getIntent().getExtras();
        cusName = bundle.getString("name");
        cusID = bundle.getString("id");
        cusAccountNo = bundle.getString("accountNo");
        cusCardNo = bundle.getString("cardNo");
        balence = bundle.getString("balence");
        ebalence = bundle.getString("emargencyBalence");
        card_status = bundle.getString("card_status");
        card_group = bundle.getString("card_group");
        version_name = bundle.getString("version_name");
        credit = bundle.getString("credit");
        unit = bundle.getString("unit");
        refund = bundle.getString("refund");


        textView.setText("Name: " + cusName + "\n"
                + "ID: " + cusID + "\n" +
                "AccountNo: " + cusAccountNo + "\n" +
                "CardNo: " + cusCardNo + "\n" +
                "Balance: " + balence + "\n" +
                "Emergency_Balance: " + ebalence + "\n" +
                "Card_Status: " + card_status + "\n" +
                "Card_Group: " + card_group + "\n" +
                "Version Name: " + version_name + "\n" +
                "Credit: " + credit + "\n" +
                "Unit: " + unit + "\n" +
                "Refund: " + refund);
    }
}
