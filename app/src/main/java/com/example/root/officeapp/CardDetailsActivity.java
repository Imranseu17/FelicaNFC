package com.example.root.officeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CardDetailsActivity extends AppCompatActivity {
    String cusName, cusID, cusAccountNo, cusCardNo,
            balence, ebalence, card_status, card_group,
            version_name, credit, unit, refund;
    String cid, Rcid;
    Long dec, Rdec;
    RequestQueue requestQueue;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        textView = findViewById(R.id.text);
        requestQueue = Volley.newRequestQueue(this);
        Bundle bundle = getIntent().getExtras();
        Rcid = bundle.getString("Rcid");
        cid = bundle.getString("cid");
        dec = bundle.getLong("dec");
        Rdec = bundle.getLong("Rdec");
        findbyCardNO();
    }

    public void findbyCardNO() {

        String url = "http://192.168.1.33:8080/api/findByCardNo/" + Rcid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    cusName = response.getString("name");
                    cusID = response.getString("id");
                    cusAccountNo = response.getString("accountNo");
                    cusCardNo = response.getString("cardNo");
                    balence = response.getString("balence");
                    ebalence = response.getString("emargencyBalence");
                    card_status = response.getString("card_status");
                    card_group = response.getString("card_group");
                    version_name = response.getString("version_name");
                    credit = response.getString("credit");
                    unit = response.getString("unit");
                    refund = response.getString("refund");

                    textView.setText("Customer Name: " + cusName + "\n"
                            + "Customer ID: " + cusID + "\n" +
                            "Customer AccountNo: " + cusAccountNo + "\n" +
                            "Customer CardNo: " + cusCardNo + "\n" +
                            "Customer  Balance: " + balence + "\n" +
                            "Customer Emergency_Balance: " + ebalence + "\n" +
                            "Customer  Card_Status: " + card_status + "\n" +
                            "Customer Card_Group: " + card_group + "\n" +
                            "Card_Decimal: " + dec + "\n" +
                            "Card_ReverseDecimal: " + Rdec + "\n" +
                            "Card_ReverseCardNo: " + "\n" + cid + "\n" +
                            "Version Name: " + version_name + "\n" +
                            "Credit: " + credit + "\n" +
                            "Unit: " + unit + "\n" +
                            "Refund: " + refund);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        });


        requestQueue.add(jsonObjectRequest);
    }
}
