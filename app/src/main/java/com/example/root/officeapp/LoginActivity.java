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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.officeapp.golobal.MainApplication;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    Button login;
    TextInputLayout username, password;
    Boolean CheckEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(" Login ");
        login = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        if (MainApplication.message == "Japanese") {
            login.setText(getResources().getString(R.string.login_japan));
            username.setHint(getResources().getString(R.string.username_japan));
            password.setHint(getResources().getString(R.string.password_japan));
        } else if (MainApplication.message == "Bangla") {
            login.setText(getResources().getString(R.string.login_ban));
            username.setHint(getResources().getString(R.string.username_ban));
            password.setHint(getResources().getString(R.string.password_ban));
        } else if (MainApplication.message == "English") {
            login.setText(getResources().getString(R.string.login_eng));
            username.setHint(getResources().getString(R.string.username_eng));
            password.setHint(getResources().getString(R.string.password_eng));
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    UserLogin();

                } else {

                    Toast.makeText(LoginActivity.this, "Please fill all form fields.", Toast.LENGTH_SHORT).show();

                }


            }

        });


    }

    public void UserLogin() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.111:8080/api/loginJava",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {


                        if (ServerResponse.contains("Data Matched")) {

                            Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(LoginActivity.this, NFCcheckActivity.class);


                            startActivity(intent);
                        } else {


                            Toast.makeText(LoginActivity.this, ServerResponse.toString(), Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {


                        Toast.makeText(LoginActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        volleyError.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("username", username.getEditText().getText().toString());
                params.put("password", password.getEditText().getText().toString());

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        requestQueue.add(stringRequest);

    }


    public void CheckEditTextIsEmptyOrNot() {
        String EmailHolder, PasswordHolder;


        EmailHolder = username.getEditText().getText().toString().trim();
        PasswordHolder = password.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {


            CheckEditText = false;

        } else {

            CheckEditText = true;
        }
    }

}

