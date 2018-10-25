package com.example.root.officeapp.nfcfelica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

final class HistoryListAdapter extends BaseAdapter {
    Context context;
    ArrayList<HistoryListData> historyList;
    LayoutInflater layoutInflater = null;

    public HistoryListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setHistoryList(ArrayList<HistoryListData> historyList) {
        this.historyList = historyList;
    }

    public int getCount() {
        return this.historyList.size();
    }

    public Object getItem(int position) {
        return this.historyList.get(position);
    }

    public long getItemId(int position) {
        return ((HistoryListData) this.historyList.get(position)).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }
}
