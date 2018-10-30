package com.example.root.officeapp.nfcfelica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public final class ErrorListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ErrorListData> errorList;
    LayoutInflater layoutInflater = null;

    public ErrorListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setErrorList(ArrayList<ErrorListData> errorList) {
        this.errorList = errorList;
    }

    public int getCount() {
        return this.errorList.size();
    }

    public Object getItem(int position) {
        return this.errorList.get(position);
    }

    public long getItemId(int position) {
        return ((ErrorListData) this.errorList.get(position)).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }
}
