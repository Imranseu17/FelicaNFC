package com.example.root.officeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.officeapp.golobal.MainApplication;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountSearchActivity extends AppCompatActivity {

    TextInputLayout cName, cID, cACNO, cCNO;
    Button search;
    String cusName, cusID, cusAccountNo, cusCardNo,
            balence, ebalence, card_status, card_group,
            version_name, credit, unit, refund;
    Boolean CheckEditText;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_search);
        setTitle(" Account Search ");
        cName = findViewById(R.id.cName);
        cID = findViewById(R.id.cID);
        cACNO = findViewById(R.id.cACNO);
        cCNO = findViewById(R.id.ccNO);
        search = findViewById(R.id.search);

        if (MainApplication.message == "Japanese") {
            cName.setHint(getResources().getString(R.string.cName_japan));
            cID.setHint(getResources().getString(R.string.cID_japan));
            cACNO.setHint(getResources().getString(R.string.cACNO_japan));
            cCNO.setHint(getResources().getString(R.string.ccNO_japan));
            search.setText(getResources().getString(R.string.search_japan));
            setTitle(R.string.accountserch_japan);
        } else if (MainApplication.message == "Bangla") {
            cName.setHint(getResources().getString(R.string.cName_ban));
            cID.setHint(getResources().getString(R.string.cID_ban));
            cACNO.setHint(getResources().getString(R.string.cACNO_ban));
            cCNO.setHint(getResources().getString(R.string.ccNO_ban));
            search.setText(getResources().getString(R.string.search_ban));
            setTitle(R.string.accountserch_ban);
        } else if (MainApplication.message == "English") {
            cName.setHint(getResources().getString(R.string.cName_eng));
            cID.setHint(getResources().getString(R.string.cID_eng));
            cACNO.setHint(getResources().getString(R.string.cACNO_eng));
            cCNO.setHint(getResources().getString(R.string.ccNO_eng));
            search.setText(getResources().getString(R.string.search_eng));
            setTitle(R.string.accountserch_eng);
        }

        requestQueue = Volley.newRequestQueue(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    if (cID.getEditText().getText().toString() != "") {

                        findByID();
                    }

                    if (cName.getEditText().getText().toString() != "") {
                        findByName();
                    }

                    if (cACNO.getEditText().getText().toString() != "") {
                        findByAccountNo();
                    }

                    if (cCNO.getEditText().getText().toString() != "") {
                        findByCardNo();
                    }


                } else {

                    Toast.makeText(AccountSearchActivity.this, "Please fill any form fields.", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    public void findByID() {


        String url = "http://192.168.1.33:8080/api/findByID/" + cID.getEditText().getText().toString().trim();

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

                    Intent intent = new Intent(AccountSearchActivity.this, CustomerInfoActivity.class);
                    intent.putExtra("name", cusName);
                    intent.putExtra("id", cusID);
                    intent.putExtra("accountNo", cusAccountNo);
                    intent.putExtra("cardNo", cusCardNo);
                    intent.putExtra("balence", balence);
                    intent.putExtra("emargencyBalence", ebalence);
                    intent.putExtra("card_status", card_status);
                    intent.putExtra("card_group", card_group);
                    intent.putExtra("version_name", version_name);
                    intent.putExtra("credit", credit);
                    intent.putExtra("unit", unit);
                    intent.putExtra("refund", refund);

                    startActivity(intent);


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

    public void findByName() {
        String url = "http://192.168.1.33:8080/api/findByName/" + cName.getEditText().getText().toString().trim();

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


                    Intent intent = new Intent(AccountSearchActivity.this, CustomerInfoActivity.class);
                    intent.putExtra("name", cusName);
                    intent.putExtra("id", cusID);
                    intent.putExtra("accountNo", cusAccountNo);
                    intent.putExtra("cardNo", cusCardNo);
                    intent.putExtra("balence", balence);
                    intent.putExtra("emargencyBalence", ebalence);
                    intent.putExtra("card_status", card_status);
                    intent.putExtra("card_group", card_group);
                    intent.putExtra("version_name", version_name);
                    intent.putExtra("credit", credit);
                    intent.putExtra("unit", unit);
                    intent.putExtra("refund", refund);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });


        requestQueue.add(jsonObjectRequest);
    }

    public void findByAccountNo() {
        String url = "http://192.168.1.33:8080/api/findByAccountNo/" + cACNO.getEditText().getText().toString().trim();

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

                    Intent intent = new Intent(AccountSearchActivity.this, CustomerInfoActivity.class);
                    intent.putExtra("name", cusName);
                    intent.putExtra("id", cusID);
                    intent.putExtra("accountNo", cusAccountNo);
                    intent.putExtra("cardNo", cusCardNo);
                    intent.putExtra("balence", balence);
                    intent.putExtra("emargencyBalence", ebalence);
                    intent.putExtra("card_status", card_status);
                    intent.putExtra("card_group", card_group);
                    intent.putExtra("version_name", version_name);
                    intent.putExtra("credit", credit);
                    intent.putExtra("unit", unit);
                    intent.putExtra("refund", refund);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });


        requestQueue.add(jsonObjectRequest);

    }

    public void findByCardNo() {
        String url = "http://192.168.1.33:8080/api/findByCardNo/" + cCNO.getEditText().getText().toString().trim();

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

                    Intent intent = new Intent(AccountSearchActivity.this, CustomerInfoActivity.class);
                    intent.putExtra("name", cusName);
                    intent.putExtra("id", cusID);
                    intent.putExtra("accountNo", cusAccountNo);
                    intent.putExtra("cardNo", cusCardNo);
                    intent.putExtra("balence", balence);
                    intent.putExtra("emargencyBalence", ebalence);
                    intent.putExtra("card_status", card_status);
                    intent.putExtra("card_group", card_group);
                    intent.putExtra("version_name", version_name);
                    intent.putExtra("credit", credit);
                    intent.putExtra("unit", unit);
                    intent.putExtra("refund", refund);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });


        requestQueue.add(jsonObjectRequest);

    }


    public void CheckEditTextIsEmptyOrNot() {
        String cNameHolder, cIDHolder, cAccountNoHolder, cCardNoHolder;


        cNameHolder = cName.getEditText().getText().toString().trim();
        cIDHolder = cID.getEditText().getText().toString().trim();
        cAccountNoHolder = cACNO.getEditText().getText().toString().trim();
        cCardNoHolder = cCNO.getEditText().getText().toString().trim();


        if (TextUtils.isEmpty(cNameHolder) && TextUtils.isEmpty(cIDHolder)
                && TextUtils.isEmpty(cAccountNoHolder) && TextUtils.isEmpty(cCardNoHolder)) {


            CheckEditText = false;

        } else {

            CheckEditText = true;
        }
    }
}

