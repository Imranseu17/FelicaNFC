package com.example.root.officeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.officeapp.golobal.MainApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardHistoryActivity extends AppCompatActivity {

    ListView listView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_history);
        listView = findViewById(R.id.cardlist);
        requestQueue = Volley.newRequestQueue(this);
        fetchingData();

    }

    void fetchingData(){
        final String myURL ="http://192.168.0.111:8080/api/findcard_history/"+ MainApplication.cardID;

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                final  String[] datetime = new String[response.length()];
                final String[]massage = new String[response.length()];
                List<String> cardHistory = new ArrayList<>();

                for (int i = 0; i < response.length();i++){
                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        datetime[i] = jsonObject.getString("datetime");
                        massage[i] = jsonObject.getString("massage");
                        cardHistory.add(datetime[i]);
                        cardHistory.add(massage[i]);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ArrayAdapter<String> adapter =   new ArrayAdapter<>(CardHistoryActivity.this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,cardHistory);

                listView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Log : "+error.getMessage());
            }
        });
            requestQueue.add(jsonArrayRequest);


    }
}
