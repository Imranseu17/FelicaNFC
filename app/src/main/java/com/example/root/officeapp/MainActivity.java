package com.example.root.officeapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.root.officeapp.golobal.MainApplication;


public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 100;
    TextView welcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome = findViewById(R.id.welcome);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (MainApplication.message == "Japanese") {
                    welcome.setText(getResources().getString(R.string.welcome_japan));
                    Intent mainIntent = new Intent(MainActivity.this, GridMenuPageActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                } else if (MainApplication.message == "Bangla") {
                    welcome.setText(getResources().getString(R.string.welcome_ban));
                    Intent mainIntent = new Intent(MainActivity.this, GridMenuPageActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                } else if (MainApplication.message == "English") {
                    welcome.setText(getResources().getString(R.string.welcome_eng));
                    Intent mainIntent = new Intent(MainActivity.this, GridMenuPageActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                } else {
                    Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);


    }


}
