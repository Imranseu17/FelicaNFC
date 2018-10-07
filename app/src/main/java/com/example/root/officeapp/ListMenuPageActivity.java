package com.example.root.officeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.root.officeapp.golobal.MainApplication;

public class ListMenuPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button read, accountSearch, report, cardInspect, settings, logout,service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu_page);
        read = findViewById(R.id.btn_read);
        accountSearch = findViewById(R.id.btn_account);
        report = findViewById(R.id.btn_report);
        cardInspect = findViewById(R.id.btn_card);
        settings = findViewById(R.id.btn_settings);
        logout = findViewById(R.id.btn_logout);
        service = findViewById(R.id.btn_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (MainApplication.message == "Japanese") {
            read.setText(getResources().getString(R.string.readcard_japan));
            accountSearch.setText(getResources().getString(R.string.accountserch_japan));
            service.setText(getResources().getString(R.string.servicecharge_japanese));
            report.setText(getResources().getString(R.string.report_japan));
            cardInspect.setText(getResources().getString(R.string.card_japan));
            settings.setText(getResources().getString(R.string.settings_japan));
            logout.setText(getResources().getString(R.string.logout_japan));
        } else if (MainApplication.message == "Bangla") {
            read.setText(getResources().getString(R.string.readcard_ban));
            accountSearch.setText(getResources().getString(R.string.accountserch_ban));
            service.setText(getResources().getString(R.string.servicecharge_ban));
            report.setText(getResources().getString(R.string.report_ban));
            cardInspect.setText(getResources().getString(R.string.card_ban));
            settings.setText(getResources().getString(R.string.settings_ban));
            logout.setText(getResources().getString(R.string.logout_ban));
        } else if (MainApplication.message == "English") {
            read.setText(getResources().getString(R.string.readcard_eng));
            accountSearch.setText(getResources().getString(R.string.accountserch_eng));
            service.setText(getResources().getString(R.string.servicecharge_eng));
            report.setText(getResources().getString(R.string.report_eng));
            cardInspect.setText(getResources().getString(R.string.card_eng));
            settings.setText(getResources().getString(R.string.settings_eng));
            logout.setText(getResources().getString(R.string.logout_eng));
        }

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListMenuPageActivity.this, ReadCard.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListMenuPageActivity.this, LoginActivity.class));
            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListMenuPageActivity.this, SettingsActivity.class));
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListMenuPageActivity.this, ReportActivity.class));
            }
        });


        accountSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListMenuPageActivity.this, AccountSearchActivity.class));
            }
        });

        cardInspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListMenuPageActivity.this, InspectCardActivity.class));
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListMenuPageActivity.this,ServiceChargeActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_menu_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up cardPropeties, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.gridView) {
            startActivity(new Intent(ListMenuPageActivity.this,GridMenuPageActivity.class));
        }

        if (id == R.id.listView) {
            return true;

        }

        if (id == R.id.recycleView) {
            startActivity(new Intent(ListMenuPageActivity.this,RecycleViewActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
