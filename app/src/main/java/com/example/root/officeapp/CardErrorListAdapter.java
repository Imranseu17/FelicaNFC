package com.example.root.officeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.officeapp.nfcfelica.ErrorListData;


import java.util.ArrayList;


public class CardErrorListAdapter   extends ArrayAdapter<ErrorListData> {

    public CardErrorListAdapter(Context context, ArrayList<ErrorListData> historyCardData) {
        super(context, 0, historyCardData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ErrorListData errorListData = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.historyview, parent, false);
        }
        // Lookup view for data population
        TextView data = (TextView) convertView.findViewById(R.id.data);
        // Populate the data into the template view using the data object
        data.setText(errorListData.getGroup()+"      "+errorListData.getType()+"      "+errorListData.getTime());
        // Return the completed view to render on screen
        return convertView;
    }
}
