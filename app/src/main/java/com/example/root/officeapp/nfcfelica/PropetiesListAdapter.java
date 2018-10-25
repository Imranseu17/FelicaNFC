package com.example.root.officeapp.nfcfelica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/* compiled from: InspectCardActivity */
final class PropetiesListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<PropetiesListData> propetiesList;

    public PropetiesListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setPropetiesList(ArrayList<PropetiesListData> propetiesList) {
        this.propetiesList = propetiesList;
    }

    public int getCount() {
        return this.propetiesList.size();
    }

    public Object getItem(int position) {
        return this.propetiesList.get(position);
    }

    public long getItemId(int position) {
        return ((PropetiesListData) this.propetiesList.get(position)).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;
    }
}
