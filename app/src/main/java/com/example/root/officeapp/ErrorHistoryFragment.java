package com.example.root.officeapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ErrorHistoryFragment extends Fragment {

    ListView listView;
    RequestQueue requestQueue;


    public ErrorHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view. findViewById(R.id.errorlist);
        requestQueue = Volley.newRequestQueue(getContext());
        fetchingData();
    }

    void fetchingData(){
        final String myURL ="http://192.168.0.111:8080/api/finderror_history/"+ MainApplication.cardID;

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                final  String[] errordate = new String[response.length()];
                final String[]massage = new String[response.length()];
                List<String> errorHistory = new ArrayList<>();

                for (int i = 0; i < response.length();i++){
                    try {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        errordate[i] = jsonObject.getString("errordate");
                        massage[i] = jsonObject.getString("massage");
                        errorHistory.add(errordate[i]);
                        errorHistory.add(massage[i]);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                ArrayAdapter<String> adapter =   new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,errorHistory);

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
